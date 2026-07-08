INSERT INTO agence (nom, code)
VALUES ('AutoFaye Dakar', 'DKR');

INSERT INTO client (nom, prenom, telephone, code)
VALUES ('Ndiaye', 'Fatou', '771234567', '0000');

INSERT INTO vehicule (immatriculation, marque, modele, tarif, disponible, agence_id)
VALUES ('DK-1234-A', 'Toyota', 'Corolla', 25000, TRUE, 1);

INSERT INTO reservation (date, prix, client_id, vehicule_id, statut)
VALUES ('2026-07-10', 25000, 1, 1, 'EN_COURS');

UPDATE vehicule
SET disponible = FALSE
WHERE id = 1;

SELECT immatriculation, marque, modele, tarif
FROM vehicule
WHERE disponible = TRUE;