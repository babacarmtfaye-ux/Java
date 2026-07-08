CREATE TABLE agence (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    telephone VARCHAR(20) NOT NULL UNIQUE,
    code VARCHAR(20) NOT NULL
);

CREATE TABLE vehicule (
    id SERIAL PRIMARY KEY,
    immatriculation VARCHAR(20) NOT NULL UNIQUE,
    marque VARCHAR(50) NOT NULL,
    modele VARCHAR(50) NOT NULL,
    tarif DECIMAL(10,2) NOT NULL CHECK (tarif > 0),
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    agence_id INT NOT NULL,
    CONSTRAINT fk_vehicule_agence
        FOREIGN KEY (agence_id) REFERENCES agence(id)
);

CREATE TABLE reservation (
    id SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    prix DECIMAL(10,2) NOT NULL CHECK (prix > 0),
    client_id INT NOT NULL,
    vehicule_id INT NOT NULL,
    statut VARCHAR(20) NOT NULL DEFAULT 'EN_COURS',
    CONSTRAINT fk_reservation_client
        FOREIGN KEY (client_id) REFERENCES client(id),
    CONSTRAINT fk_reservation_vehicule
        FOREIGN KEY (vehicule_id) REFERENCES vehicule(id)
);