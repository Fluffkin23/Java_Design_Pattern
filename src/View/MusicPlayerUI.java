package View;

import Controller.MusicController;
import Model.MusicLibrary;
import Model.Playlist;
import Model.Song;
import Observer.MusicControllerObserver;


public class MusicPlayerUI implements MusicControllerObserver
{
    private MusicController musicController;

    public MusicPlayerUI(MusicController musicController)
    {
        this.musicController = musicController;
        musicController.subscribe(this);
    }

    @Override
    public void update()
    {


    }

    public void displayPlaylist(Playlist playlist)
    {
        // Display the playlist on the UI
        System.out.println("Playlist: " + playlist.getName());
        for (Song song : playlist.getSongs()) {
            System.out.println(song.getTitle() + " by " + song.getArtist());
        }
    }

    public void displayLibrary(MusicLibrary library) {
        // Display the music library on the UI
        System.out.println("Music Library:");
        // Assuming the library has a method to get all songs
        for (Song song : library.getSongs())
        {
            System.out.println(song.getTitle() + " by " + song.getArtist());
        }
    }
}
