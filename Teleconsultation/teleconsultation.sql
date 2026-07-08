CREATE TABLE Patient (

    numeroSecuriteSociale VARCHAR(20) PRIMARY KEY,

    nom VARCHAR(50) NOT NULL,

    prenom VARCHAR(50) NOT NULL,

    email VARCHAR(100),

    dateNaissance DATE NOT NULL
);




CREATE TABLE Medecin (

    numeroOrdre VARCHAR(20) PRIMARY KEY,

    nom VARCHAR(50) NOT NULL,

    prenom VARCHAR(50) NOT NULL,

    specialite VARCHAR(50) NOT NULL,

    tarifBase DECIMAL(10,2) NOT NULL
);



CREATE TABLE Consultation (

    numero VARCHAR(20) PRIMARY KEY,

    dateConsultation DATE NOT NULL,

    heureDebut TIME NOT NULL,

    heureFin TIME NOT NULL,

    statut VARCHAR(20) NOT NULL,

    prixDefinitif DECIMAL(10,2),


    numeroSecuriteSociale VARCHAR(20) NOT NULL,

    numeroOrdre VARCHAR(20) NOT NULL,


    CONSTRAINT fk_consultation_patient
        FOREIGN KEY(numeroSecuriteSociale)
        REFERENCES Patient(numeroSecuriteSociale),


    CONSTRAINT fk_consultation_medecin
        FOREIGN KEY(numeroOrdre)
        REFERENCES Medecin(numeroOrdre)
);




CREATE TABLE Paiement (

    idTransaction VARCHAR(20) PRIMARY KEY,

    datePaiement DATE NOT NULL,

    montantPaye DECIMAL(10,2) NOT NULL,

    modePaiement VARCHAR(30) NOT NULL,

    numeroConsultation VARCHAR(20) NOT NULL,


    CONSTRAINT fk_paiement_consultation
        FOREIGN KEY(numeroConsultation)
        REFERENCES Consultation(numero)
);


CREATE TABLE Avis (

    idAvis VARCHAR(20) PRIMARY KEY,

    note INT,

    commentaire TEXT,

    numeroConsultation VARCHAR(20) NOT NULL,


    CONSTRAINT fk_avis_consultation
        FOREIGN KEY(numeroConsultation)
        REFERENCES Consultation(numero)
);



ALTER TABLE Avis

ADD CONSTRAINT check_note

CHECK(note BETWEEN 0 AND 5);



INSERT INTO Patient
(
    numeroSecuriteSociale,
    nom,
    prenom,
    email,
    dateNaissance
)

VALUES
(
    'SN123456789',
    'Faye',
    'Babacar',
    'babacar@gmail.com',
    '2002-05-15'
);




UPDATE Medecin

SET tarifBase = 20000

WHERE numeroOrdre = 'MED-001';




SELECT

    nom,

    prenom,

    specialite

FROM Medecin;