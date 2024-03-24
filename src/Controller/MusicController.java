package Controller;

import Model.Song;
import Strategy.PlaybackStrategy;

import java.util.List;

public class MusicController
{
    private PlaybackStrategy strategy;
    private List<Song> playlist;
    private int currentSongIndex;

    public MusicController()
    {

    }

    public void setStrategy(PlaybackStrategy strategy)
    {
        this.strategy = strategy;
    }

    public void playSongAtIndex(int index) {
        currentSongIndex = index;
        Song song = playlist.get(index);
        // ... initiate playback of the song
        // This would be the place to interface with your actual media player API/library
    }

    public void playSong(Song song)
    {
        // Logic to play the song
        System.out.println("Now playing: " + song.getDetails());
        // This would involve more complex logic, potentially involving Java's sound API.
    }

    // Method to pause a song
    public void pauseSong(Song song) {
        // Logic to pause the song
        System.out.println("Pausing: " + song.getDetails());
        // Similarly, actual pause logic would be more involved.
    }

    // ... rest of the MusicController methods
}

