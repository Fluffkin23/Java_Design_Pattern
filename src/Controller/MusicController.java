package Controller;

import Model.MusicLibrary;
import Model.Playlist;
import Observer.MusicControllerObserver;
import Strategy.PlaybackStrategy;
import java.util.ArrayList;
import java.util.List;

public class MusicController {
    private MusicLibrary musicLibrary;
    private Playlist currentPlaylist;
    private int currentSongIndex = -1;
    private PlaybackStrategy strategy;
    private List<MusicControllerObserver> observers = new ArrayList<>();

    public MusicController(MusicLibrary musicLibrary) {
        this.musicLibrary = musicLibrary;
    }

    public void playSong() {
        // Your logic for playing a song
        notifyObservers();
    }

    public void pauseSong() {
        // Your logic for pausing a song
        notifyObservers();
    }

    // ... other methods ...

    public void subscribe(MusicControllerObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(MusicControllerObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (MusicControllerObserver observer : observers) {
            observer.update();
        }
    }

    // ... setters and getters ...
}
