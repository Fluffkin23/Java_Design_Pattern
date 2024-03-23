package Model;

import Observer.LibraryObserver;

import java.util.ArrayList;
import java.util.List;

public class MusicLibrary
{
    private List<Song> songs;
    private List<LibraryObserver> subscribers;

    public MusicLibrary()
    {
        this.songs = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public void addSong(Song song) {
        this.songs.add(song);
        notifySubscribers();
    }

    public void removeSong(int index) {
        if(index >= 0 && index < songs.size()) {
            songs.remove(index);
            notifySubscribers(); // Notify subscribers about the change
        }
    }

    public List<Song> getSongs()
    {
        return new ArrayList<>(songs); // Returns a copy of the list to prevent external modification.
    }

    public void subscribe(LibraryObserver subscriber)
    {
        subscribers.add(subscriber);
    }

    public void unsubscribe(LibraryObserver subscriber)
    {
        subscribers.remove(subscriber);
    }

    //The notifySubscribers method is marked as private because it's an internal mechanism for the MusicLibrary class
    // to update all its subscribers about changes. It is called whenever a song is added or removed from the library.

    private void notifySubscribers() {
        for (LibraryObserver subscriber : subscribers) {
            subscriber.update();
        }
    }
}
