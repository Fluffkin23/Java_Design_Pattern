package Controller;

import Model.MusicLibrary;
import Model.Playlist;
import Model.Song;
import Observer.MusicControllerObserver;
import Strategy.PlaybackStrategy;

import java.util.ArrayList;
import java.util.List;

public class MusicController
{

    private MusicLibrary musicLibrary;
    private Playlist currentPlaylist;
    private int currentSong = -1; // Assuming -1 means no song is selected

    private List<MusicControllerObserver> observers = new ArrayList<>();


    public MusicController()
    {
        musicLibrary = new MusicLibrary();
    }

    // Getters and setters
    public MusicLibrary getMusicLibrary()
    {
        return musicLibrary;
    }

    public void setMusicLibrary(MusicLibrary musicLibrary)
    {
        this.musicLibrary = musicLibrary;
        notifySubscribers();
    }

    public Playlist getCurrentPlaylist()
    {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(Playlist currentPlaylist)
    {
        this.currentPlaylist = currentPlaylist;
        notifySubscribers();
    }

    public int getCurrentSong()
    {
        return currentSong;
    }

    public void setCurrentSong(int currentSong)
    {
        this.currentSong = currentSong;
        notifySubscribers();
    }

    // Observer-related methods
    public void subscribe(MusicControllerObserver observer)
    {
        observers.add(observer);
    }

    public void unsubscribe(MusicControllerObserver observer)
    {
        observers.remove(observer);
    }

    private void notifySubscribers()
    {
        for (MusicControllerObserver observer : observers)
        {
            observer.update();
        }
    }


    public void selectSong(int song)
    {
        currentSong = song;
        notifySubscribers();
    }

    public void playSong()
    {
        if(currentSong >= 0)
        {
            // Logic to play the song
           System.out.println( musicLibrary.getSongByIndex(currentSong).getTitle() );
            musicLibrary.getSongByIndex(currentSong).play();
            notifySubscribers();
            // This would involve more complex logic, potentially involving Java's sound API.
        }

    }

    // Method to pause a song
    public void pauseSong(Song song)
    {
        // Logic to pause the song
        System.out.println("Pausing: " + song.getDetails());
        // Similarly, actual pause logic would be more involved.
        notifySubscribers();
    }

    public void changeVolume(int level)
    {
        notifySubscribers();
    }

    // ... rest of the MusicController methods
}

