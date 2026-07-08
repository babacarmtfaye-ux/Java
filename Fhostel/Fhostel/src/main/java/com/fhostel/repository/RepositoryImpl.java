package com.fhostel.repository;

import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryImpl<T> implements Repository<T> {

    protected List<T> elements = new ArrayList<>();

    @Override
    public void ajouter(T element) {
        elements.add(element);
    }

    @Override
    public List<T> trouverTous() {
        return elements;
    }
}