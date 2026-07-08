package com.autofaye.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.autofaye.model.Vehicule;

public class VehiculeRepositoryJdbc implements VehiculeRepository {

    private final Connection connection;

    public VehiculeRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Vehicule ajouter(Vehicule vehicule) {
        String sql = "INSERT INTO vehicule (immatriculation, marque, modele, tarif, disponible, agence_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vehicule.getImmatriculation());
            stmt.setString(2, vehicule.getMarque());
            stmt.setString(3, vehicule.getModele());
            stmt.setDouble(4, vehicule.getTarif());
            stmt.setBoolean(5, vehicule.estDisponible());
            stmt.setInt(6, 1);
            stmt.executeUpdate();

            ResultSet cles = stmt.getGeneratedKeys();
            if (cles.next()) {
                String idGenere = String.valueOf(cles.getInt(1));
                return trouverParId(idGenere).orElseThrow();
            }
            throw new RuntimeException("Echec de la creation du vehicule");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Vehicule> trouverParId(String vehiculeId) {
        String sql = "SELECT * FROM vehicule WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(vehiculeId));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(construireVehicule(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicule> trouverDisponibles() {
        String sql = "SELECT * FROM vehicule WHERE disponible = TRUE";
        List<Vehicule> vehicules = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vehicules.add(construireVehicule(rs));
            }
            return vehicules;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Vehicule construireVehicule(ResultSet rs) throws SQLException {
        Vehicule vehicule = new Vehicule(
                String.valueOf(rs.getInt("id")),
                rs.getString("immatriculation"),
                rs.getString("marque"),
                rs.getString("modele"),
                rs.getDouble("tarif")
        );

        if (!rs.getBoolean("disponible")) {
            vehicule.marquerCommeReserve();
        }

        return vehicule;
    }
}