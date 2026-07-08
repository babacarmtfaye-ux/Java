package com.fhostel.repository;

import java.util.List;

public interface Repository<T> {

    void ajouter(T element);

    List<T> trouverTous();
}