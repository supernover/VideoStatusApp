package com.video.status_downloader.supernover;


public interface Subject {
    void register(Observer observer);

    void unregister(Observer observer);

    void notifyObservers();
}