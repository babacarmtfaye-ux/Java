package com.autofaye.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.autofaye.model.Client;
import com.autofaye.model.Reservation;
import com.autofaye.model.StatutReservation;
import com.autofaye.model.Vehicule;

public class ReservationRepositoryJdbc implements ReservationRepository {

    private static final String TABLE_RESERVATION = "reservation";
    private static final String COLONNE_STATUT = "statut";

    private final Connection connection;
    private final ClientRepository clientRepository;
    private final VehiculeRepository vehiculeRepository;

    public ReservationRepositoryJdbc(Connection connection, ClientRepository clientRepository, VehiculeRepository vehiculeRepository) {
        this.connection = connection;
        this.clientRepository = clientRepository;
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public Reservation ajouter(Reservation reservation) {
        String sql = "INSERT INTO " + TABLE_RESERVATION + " (date, prix, client_id, vehicule_id, statut)"
                + " VALUES (?, ?, ?, ?, ?) RETURNING id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new Date(reservation.getDate().getTime()));
            stmt.setDouble(2, reservation.getPrix());
            stmt.setInt(3, Integer.parseInt(reservation.getClient().getId()));
            stmt.setInt(4, Integer.parseInt(reservation.getVehicule().getId()));
            stmt.setString(5, reservation.getStatut().name());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String idGenere = rs.getString("id");
                return trouverParId(idGenere).orElseThrow();
            }
            throw new RuntimeException("Echec de la creation de la reservation");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reservation modifier(Reservation reservation) {
        String sql = "UPDATE " + TABLE_RESERVATION + " SET date = ?, prix = ?, statut = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new Date(reservation.getDate().getTime()));
            stmt.setDouble(2, reservation.getPrix());
            stmt.setString(3, reservation.getStatut().name());
            stmt.setInt(4, Integer.parseInt(reservation.getId()));
            stmt.executeUpdate();
            return reservation;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> trouverToutes() {
        String sql = "SELECT * FROM " + TABLE_RESERVATION;
        List<Reservation> reservations = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservations.add(construireReservation(rs));
            }
            return reservations;
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("Table \"reservation\" not found")) {
                return reservations;
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> trouverParClientId(String clientId) {
        String sql = "SELECT * FROM " + TABLE_RESERVATION + " WHERE client_id = ?";
        List<Reservation> reservations = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(clientId));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservations.add(construireReservation(rs));
            }
            return reservations;
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("Table \"reservation\" not found")) {
                return reservations;
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Reservation> trouverParId(String id) {
        String sql = "SELECT * FROM " + TABLE_RESERVATION + " WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(construireReservation(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Reservation construireReservation(ResultSet rs) throws SQLException {
        String clientId = String.valueOf(rs.getInt("client_id"));
        String vehiculeId = String.valueOf(rs.getInt("vehicule_id"));

        Client client = clientRepository.trouverParId(clientId)
                .orElseThrow(() -> new RuntimeException("Client introuvable pour la reservation"));
        Vehicule vehicule = vehiculeRepository.trouverParId(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule introuvable pour la reservation"));

        Reservation reservation = new Reservation(
                String.valueOf(rs.getInt("id")),
                rs.getDate("date"),
                rs.getDouble("prix"),
                client,
                vehicule
        );

        try {
            ResultSetMetaData metaData = rs.getMetaData();
            boolean hasStatutColumn = false;
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                if (COLONNE_STATUT.equalsIgnoreCase(metaData.getColumnLabel(i))) {
                    hasStatutColumn = true;
                    break;
                }
            }

            if (hasStatutColumn) {
                String statut = rs.getString(COLONNE_STATUT);
                if (statut != null && StatutReservation.TERMINEE.name().equals(statut)) {
                    reservation.terminer();
                }
            }
        } catch (SQLException ignored) {
            // Compatibilité avec des bases existantes sans colonne statut.
        }

        return reservation;
    }

}
