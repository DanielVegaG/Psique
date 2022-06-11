package com.example.psique.Listeners;

/**
 * Se le llama si sale bien el crear un nuevo chat
 */
public interface ILoadTimeFromFirebaseListener {
    void onLoadOnlyTimeSuccess(long estimateTimeInMs);
}
