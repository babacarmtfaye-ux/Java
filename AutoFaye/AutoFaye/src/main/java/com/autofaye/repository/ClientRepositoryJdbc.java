package com.autofaye.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.autofaye.model.Client;

public class ClientRepositoryJdbc implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Client> trouverParId(String clientId) {
        String sql = "SELECT * FROM client WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(clientId));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(construireClient(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> trouverParTelephoneEtCode(String telephone, String code) {
        String sql = "SELECT * FROM client WHERE telephone = ? AND code = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, telephone);
            stmt.setString(2, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(construireClient(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> trouverParTelephone(String telephone) {
        String sql = "SELECT * FROM client WHERE telephone = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, telephone);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(construireClient(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client creer(Client client) {
        String sql = "INSERT INTO client (nom, prenom, telephone, code) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getTelephone());
            stmt.setString(4, client.getCode());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return trouverParId(String.valueOf(generatedKeys.getInt(1))).orElseThrow();
            }
            throw new IllegalStateException("Impossible de créer le client");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Client construireClient(ResultSet rs) throws SQLException {
        return new Client(
                String.valueOf(rs.getInt("id")),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("telephone"),
                rs.getString("code")
        );
    }
}
