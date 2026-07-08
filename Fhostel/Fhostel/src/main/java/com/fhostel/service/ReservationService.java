package com.fhostel.service;

import com.fhostel.model.Chambre;
import com.fhostel.model.Client;
import com.fhostel.model.Reservation;
import com.fhostel.repository.ChambreRepository;
import com.fhostel.repository.ClientRepository;
import com.fhostel.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReservationService {

    private final ChambreRepository chambreRepository;
    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(ChambreRepository chambreRepository,
            ClientRepository clientRepository,
            ReservationRepository reservationRepository) {
        this.chambreRepository = chambreRepository;
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
    }

    public Reservation checkIn(String numeroChambre, String cni, String nom, String telephone,
            LocalDate dateArrivee, LocalDate dateDepart) {

        Chambre chambre = trouverChambreDisponible(numeroChambre);
        ValidationUtils.requireVrai(dateDepart.isAfter(dateArrivee), "Date de depart invalide");

        Client client = trouverOuCreerClient(cni, nom, telephone);

        Reservation reservation = new Reservation(UUID.randomUUID().toString(), client, chambre,
                dateArrivee, dateDepart);
        reservationRepository.ajouter(reservation);

        chambre.changerStatut(Chambre.OCCUPEE);

        return reservation;
    }

    public double checkOut(String numeroReservation, LocalTime heureDepart) {

        Reservation reservation = trouverReservationOccupee(numeroReservation);

        double supplement = reservation.calculerSupplement(heureDepart);
        double montant = reservation.getChambre().getPrixNuitee() + supplement;
        reservation.setMontantFacture(montant);

        reservation.cloturer();
        reservation.getChambre().changerStatut(Chambre.EN_NETTOYAGE);

        return montant;
    }

    public void reglerFacture(String numeroReservation, double montantPaye) {

        Reservation reservation = trouverReservation(numeroReservation);

        ValidationUtils.requireFaux(reservation.isReglee(), "Facture deja reglee");
        ValidationUtils.requireVrai(montantPaye >= reservation.getMontantFacture(), "Montant insuffisant");

        reservation.marquerCommeReglee();
    }

    private Chambre trouverChambreDisponible(String numeroChambre) {
        Chambre chambre = chambreRepository.trouverParNumero(numeroChambre)
                .orElseThrow(() -> new FhostelException("Chambre introuvable"));
        ValidationUtils.requireVrai(chambre.getStatut().equals(Chambre.DISPONIBLE), "Chambre non disponible");
        return chambre;
    }

    private Reservation trouverReservation(String numeroReservation) {
        return reservationRepository.trouverParId(numeroReservation)
                .orElseThrow(() -> new FhostelException("Reservation introuvable"));
    }

    private Reservation trouverReservationOccupee(String numeroReservation) {
        Reservation reservation = trouverReservation(numeroReservation);
        ValidationUtils.requireVrai(reservation.getChambre().getStatut().equals(Chambre.OCCUPEE),
                "Chambre non occupee");
        return reservation;
    }

    private Client trouverOuCreerClient(String cni, String nom, String telephone) {
        Optional<Client> clientExistant = clientRepository.trouverParCNI(cni);
        if (clientExistant.isPresent()) {
            return clientExistant.get();
        }
        Client nouveauClient = new Client(UUID.randomUUID().toString(), nom, telephone, "0000", cni);
        clientRepository.ajouter(nouveauClient);
        return nouveauClient;
    }

    public List<Reservation> historiqueSejours(String cni) {
    return reservationRepository.trouverParClient(cni);
}
}
