package Controller;

import Model.MusicLibrary;
import Model.Playlist;
import Model.PlaylistLibrary;
import Model.Song;
import Observer.MusicControllerObserver;
import Strategy.NormalPlayback;
import Strategy.PlaybackStrategy;
import Strategy.RepeatPlayback;
import Strategy.ShufflePlayback;

import java.util.ArrayList;
import java.util.List;

public class MusicController
{

    private MusicLibrary musicLibrary;
    private PlaylistLibrary playlistLibrary;
    private PlaybackStrategy playbackStrategy;
    private Playlist playlist;
    private List<MusicControllerObserver> observers = new ArrayList<>();


    public MusicController()
    {
        this.musicLibrary = new MusicLibrary();
        this.playlistLibrary = new PlaylistLibrary();
        this.playlist = new Playlist("Default");
        this.playbackStrategy = new NormalPlayback();

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

    public PlaylistLibrary getPlaylistLibrary() {
        return playlistLibrary;
    }

    public void setPlaylistLibrary(PlaylistLibrary playlistLibrary)
    {
        this.playlistLibrary = playlistLibrary;
    }


    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void loadSongFromPlaylist()
    {
        this.playlist.loadSongs();
        this.playlist.getPlaylistName();
    }

    public PlaybackStrategy getPlaybackStrategy() {
        return playbackStrategy;
    }

    public void setPlaybackStrategy(PlaybackStrategy playbackStrategy) {
        this.playbackStrategy = playbackStrategy;
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

    public void notifySubscribers()
    {
        Song currentSong = playbackStrategy.getCurrentSong();
        String title = (currentSong != null) ? currentSong.getTitle() : "No song playing";
        String artist = (currentSong != null) ? currentSong.getArtist() : "Unknown";

        for (MusicControllerObserver observer : observers)
        {
            observer.update(title,artist);
        }
    }



    public void playSong()
    {
        if (playlist != null && !playlist.getPlaylistSongs().isEmpty()) {
            playbackStrategy.play(playlist.getPlaylistSongs());
            // currentSong index might be managed inside the playback strategy now
            System.out.println("PLAYING NOW : " + playbackStrategy.getCurrentSongIndex());
        }
        else
        {
            System.out.println("Playlist is empty.");
        }
        notifySubscribers();
    }

    // Method to pause a song
    public void pauseSong()
    {
        if (playbackStrategy != null) {
            playbackStrategy.pause();
            // Notify observers if necessary
        }
    }

    public void resumeSong()
    {
        if(playbackStrategy!= null)
        {
            playbackStrategy.resume();
        }
    }

    public void nextSong()
    {
        if(playbackStrategy!= null)
        {
            playbackStrategy.next();
            System.out.println("PLAYING NOW : " + playbackStrategy.getCurrentSongIndex());
        }
        notifySubscribers();
    }

    public void previousSong()
    {
        if(playbackStrategy!= null)
        {
            playbackStrategy.previous();
        }
        notifySubscribers();
    }

    public void toggleRepeatPlayblack()
    {

        int currentSongIndex = playbackStrategy.getCurrentSongIndex();
        if (!(playbackStrategy instanceof RepeatPlayback))
        {
            playbackStrategy.pause();
            playbackStrategy = new RepeatPlayback();
        }
        else
        {
            playbackStrategy.pause();
            playbackStrategy = new NormalPlayback();
        }
        // Ensure to carry the current song index to the new strategy

        playbackStrategy.setCurrentSongIndex(currentSongIndex);
        playbackStrategy.play(playlist.getPlaylistSongs());
        notifySubscribers();
    }



    public void toggleShufflePlayback()
    {
        int currentIndex = playbackStrategy.getCurrentSongIndex();
        // Check if the current strategy is ShufflePlayback
        if (!(playbackStrategy instanceof ShufflePlayback)) {
            // Save the current song index before switching strategies

            // Switch to shuffle playback
            playbackStrategy.pause();
            playbackStrategy = new ShufflePlayback();
            notifySubscribers();
            // If you want to start shuffle from the current song, you could try to find its index in the new shuffled list
            // But generally, shuffle starts from a random song
            // playbackStrategy.setCurrentSongIndex(currentIndex);
            System.out.println("Shuffle mode enabled.");
        }
        else
        {
            playbackStrategy.pause();
            playbackStrategy = new NormalPlayback();
            notifySubscribers();

        }
        // Optionally, you can start playing the shuffled playlist immediately
        playbackStrategy.setCurrentSongIndex(currentIndex);
        playbackStrategy.play(playlist.getPlaylistSongs());
        notifySubscribers();
    }

    public void changeVolume(int volumePercentage)
    {
        // Convert the slider's percentage to a decibel value within the allowed range.
        float volume = convertPercentageToDecibels(volumePercentage);
        // Assuming all your playback strategies have a setVolume method.
        if (playbackStrategy != null) {
            playbackStrategy.setVolume(volume);
        }
    }

    private float convertPercentageToDecibels(int percentage) {
        // Example conversion, needs to be adapted to your actual range and needs
        float min = -80.0f; // Minimum decibels (mute)
        float max = 6.0f;   // Maximum decibels
        return (max - min) * (percentage / 100.0f) + min;

        // ... rest of the MusicController methods
    }
}

