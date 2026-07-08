package com.autofaye.exception;

public class InvalidVehicleDataException extends RuntimeException {
    public InvalidVehicleDataException(String message) {
        super(message);
    }
}