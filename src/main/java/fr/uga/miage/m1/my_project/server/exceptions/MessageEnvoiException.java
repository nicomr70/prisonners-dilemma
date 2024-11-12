package fr.uga.miage.m1.my_project.server.exceptions;

public class MessageEnvoiException extends RuntimeException {
    public MessageEnvoiException(String message) {
        super(message);
    }

    public MessageEnvoiException(String message, Throwable cause) {
        super(message, cause);
    }
}
