package Model;

import Observer.PlaylistObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist
{
    private List<Song> songs;
    private String name;
    private List<PlaylistObserver> observers;

    public Playlist(String name)
    {
        this.name = name;
        this.songs = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
        notifyObservers();
    }

    public void removeSong(Song song) {
        songs.remove(song);
        notifyObservers();
    }

    public void play() {
        for (Song song : songs) {
            song.play(); // Assuming the Song interface has a play method.
        }
        // Optionally notify observers that the playlist has started playing
        notifyObservers();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Playlist: " + name + "\n");
        for (Song song : songs) {
            sb.append(song.getDetails()).append("\n");
        }
        return sb.toString();
    }

    public List<Song>getSongs()
    {
        return songs;
    }

    public String getName()
    {
        return name;
    }

    public void shuffle()
    {
        Collections.shuffle(songs);
    }

    public void subscribe(PlaylistObserver observer)
    {
        this.observers.add(observer);
    }

    public void unsubscribe(PlaylistObserver observer)
    {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (PlaylistObserver observer : observers) {
            observer.update(this);
        }
    }
}
