package com.autofaye.repository;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.h2.tools.RunScript;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.autofaye.model.Reservation;
import com.autofaye.model.StatutReservation;

class ReservationRepositoryJdbcTest {

    private Connection connection;
    private ReservationRepositoryJdbc repository;

    @BeforeEach
    void setUp() throws Exception {
        connection = DriverManager.getConnection(
                "jdbc:h2:mem:autofaye-test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DATABASE_TO_UPPER=false",
                "sa",
                ""
        );

        String schema = Files.readString(Path.of("src/main/resources/db/schema.sql"));
        String seed = Files.readString(Path.of("src/main/resources/db/seed.sql"));

        RunScript.execute(connection, new StringReader(schema));
        RunScript.execute(connection, new StringReader(seed));

        ClientRepositoryJdbc clientRepository = new ClientRepositoryJdbc(connection);
        VehiculeRepositoryJdbc vehiculeRepository = new VehiculeRepositoryJdbc(connection);
        repository = new ReservationRepositoryJdbc(connection, clientRepository, vehiculeRepository);
    }

    @Test
    void trouverToutesRetourneLesReservationsAvecUnStatutValide() {
        List<Reservation> reservations = repository.trouverToutes();

        assertFalse(reservations.isEmpty());
        assertEquals(1, reservations.size());
        assertEquals(StatutReservation.EN_COURS, reservations.get(0).getStatut());
    }
}
