package com.autofaye.service;

import com.autofaye.model.Vehicule;
import java.util.Date;

public class TarificationService {

    public double calculerPrix(Vehicule vehicule, Date dateDebut, Date dateFin) {
        long jours = (dateFin.getTime() - dateDebut.getTime()) / (1000 * 60 * 60 * 24);
        return jours * vehicule.getTarif();
    }
}