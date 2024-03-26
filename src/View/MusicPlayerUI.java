package View;

import Controller.MusicController;
import Model.MusicLibrary;
import Model.Playlist;
import Model.PlaylistManager;
import Model.Song;
import Observer.MusicControllerObserver;

import javax.swing.*;
import java.awt.*;


public class MusicPlayerUI extends JFrame implements MusicControllerObserver
{
    private MusicController musicController;
    private JLabel trackTitle, trackInfo;
    private ImageIcon albumArtLabel;
    private PlaylistManager playlistManager; // Add playlistManager as a class member


    public MusicPlayerUI(MusicController musicController)
    {
        this.musicController = musicController;
        musicController.subscribe(this);
        this.playlistManager = new PlaylistManager(); // Initialize once here

        initializeUI();
    }

    private void initializePlayerTab(JTabbedPane tabbedPane)
    {
        // Similar to your existing setup
        JPanel playerPanel = new JPanel(new BorderLayout());
        // Setup album art, track info, controls, etc.
        // Omitted for brevity; use your existing code as basis

        tabbedPane.addTab("Player", playerPanel);
    }

    private void initializeSongTab(JTabbedPane tabbedPane)
    {
        SongView songView = new SongView();
        tabbedPane.addTab("Add Song", songView);
    }

    private void initializeLibraryTab(JTabbedPane tabbedPane)
    {
        LibraryView libraryView = new LibraryView(musicController.getMusicLibrary());
        tabbedPane.addTab("Library", libraryView);
    }

    private void initializePlaylistTab(JTabbedPane tabbedPane)
    {
        PlaylistView playlistView = new PlaylistView(playlistManager, playlistManager.getPlaylist("Default Playlist"));// Use the class member
        tabbedPane.addTab("Playlist", playlistView);
        playlistView.loadPlaylistNamesIntoComboBox(); // Make sure to call this method

    }

    private void initializeUI() {
        setTitle("Spotify-Like App");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Initialize tabs here (Player, Library, Playlist)
        initializePlayerTab(tabbedPane);
        initializeLibraryTab(tabbedPane);
        initializePlaylistTab(tabbedPane);
        initializeSongTab(tabbedPane);

        add(tabbedPane);
    }


    @Override
    public void update()
    {
        // Update UI components based on the state of the music controller
        // For instance, update track title, artist, album art, etc.

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

    // for testing GUI
    public static void main(String[] args)
    {
        MusicController controller = new MusicController();
        // Make sure controller is initialized with a Playlist
        Playlist playlist = new Playlist("Favourite"); // Assuming you have a default constructor
        // Populate your playlist if necessary
        controller.setCurrentPlaylist(playlist); // Assuming you have a setter method

        SwingUtilities.invokeLater(() -> {
            MusicPlayerUI app = new MusicPlayerUI(controller);
            app.setVisible(true);
        });
    }
}
