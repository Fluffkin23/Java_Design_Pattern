package Model;

import Observer.LibraryObserver;
import java.util.ArrayList;
import java.util.List;

public class MusicLibrary
{
    private List<Song> songs = new ArrayList<>();
    private List<LibraryObserver> subscribers = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(song);
        notifySubscribers();
    }

    public void removeSong(Song song) {
        songs.remove(song);
        notifySubscribers();
    }

    public Song findSongByTitle(String title) {
        return songs.stream()
                .filter(song -> song.getDetails().contains(title))
                .findFirst()
                .orElse(null);
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs); // Return a copy of the list to avoid external modification
    }

    public void subscribe(LibraryObserver subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(LibraryObserver subscriber) {
        subscribers.remove(subscriber);
    }

    private void notifySubscribers() {
        for (LibraryObserver subscriber : subscribers) {
            subscriber.update();
        }
    }
}
