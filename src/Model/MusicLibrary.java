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

    public void addSong(Song song)
    {
        this.songs.add(song);
        notifySubscriber();
    }

    public void removeSong(int index)
    {
        if(index >= 0 && index < songs.size() )
        {
            Song song = songs.remove(index);
            // add notify method.
        }
    }

    public List<Song> getSongs()
    {
        return new ArrayList<>(songs); // Returns a copy of the list to prevent external modification.
    }

    public Song getSongByIndex(int index)
    {
        if (index >= 0 && index < songs.size())
        {
            return songs.get(index);
        } else {
            // Handle the case where the index is out of bounds
            throw new IndexOutOfBoundsException("No song at index: " + index);
        }
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

    //Call this method whenever the library is updated
    private void notifySubscriber()
    {
        for (LibraryObserver subscriber : subscribers)
        {
            subscriber.update();
        }
    }
}
