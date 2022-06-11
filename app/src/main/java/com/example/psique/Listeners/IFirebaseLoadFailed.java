package com.example.psique.Listeners;

/**
 * Interfaz por si fallan las notificaciones
 */
public interface IFirebaseLoadFailed {
    void onError(String message);
}
