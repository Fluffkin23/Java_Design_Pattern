package View;

import Controller.MusicController;
import Model.MusicLibrary;
import Model.Playlist;
import Model.Song;
import Observer.MusicControllerObserver;

import javax.swing.*;
import java.awt.*;


public class MusicPlayerUI extends JFrame implements MusicControllerObserver
{
    private MusicController musicController;
    private JLabel trackTitle, trackInfo;
    private ImageIcon albumArtLabel;


    public MusicPlayerUI(MusicController musicController)
    {
        this.musicController = musicController;
        musicController.subscribe(this);
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
        LibraryView libraryView = new LibraryView(musicController);
        tabbedPane.addTab("Library", libraryView);
    }

    private void initializePlaylistTab(JTabbedPane tabbedPane)
    {
        PlaylistView playlistView = new PlaylistView(musicController, musicController.getPlaylist("Default Playlist"));// Use the class member
        tabbedPane.addTab("Playlist", playlistView);
        playlistView.loadPlaylistNamesIntoComboBox(); // Make sure to call this method

    }

    private void initializeUI() {
        // Set up the main window
        setTitle("Spotify-Like App");
        setSize(650, 650); // Set the size of the app
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        getContentPane().setBackground(new Color(0, 0, 0));

        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Player Tab
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setLayout(new BorderLayout());
        playerPanel.setBackground(new Color(0, 0, 0));

        // Album and track info
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(255, 255, 255));
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Resize and add album art with horizontal centering
        ImageIcon originalIcon = new ImageIcon("src/img/song sample picture.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel albumArtLabel = new JLabel(resizedIcon);

        gbc.gridx = 0; // X position
        gbc.gridy = 0; // Y position
        gbc.gridwidth = 1; // Number of columns the component occupies
        gbc.gridheight = 1; // Number of rows the component occupies
        gbc.weightx = 1.0; // Give extra horizontal space
        gbc.weighty = 1.0; // Give extra vertical space
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allow horizontal stretching
        gbc.anchor = GridBagConstraints.CENTER; // Center the component

        infoPanel.add(albumArtLabel, gbc);

        // Add track title with horizontal centering
        JLabel trackTitle = new JLabel("Track Title", SwingConstants.CENTER);

        gbc.gridy = 1; // Move to the next row
        gbc.anchor = GridBagConstraints.CENTER; // Center the component
        infoPanel.add(trackTitle, gbc);

        // Add artist and album name with horizontal centering
        JLabel trackInfo = new JLabel("Track info: Artist", SwingConstants.CENTER);
        gbc.gridy = 2; // Move to the next row
        infoPanel.add(trackInfo, gbc);

        // Playback controls
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        controlsPanel.setBackground(new Color(0, 0, 0));
        controlsPanel.add(new JButton("Shuffle"));
        controlsPanel.add(new JButton("Previous"));
        controlsPanel.add(new JButton("Play"));
        controlsPanel.add(new JButton("Next"));
        controlsPanel.add(new JButton("Repeat"));

        // Progress bar
        JSlider progressBar = new JSlider();
        progressBar.setValue(25);

        // Adding components to playerPanel
        playerPanel.add(infoPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(255, 255, 255));
        bottomPanel.add(controlsPanel, BorderLayout.NORTH);
        bottomPanel.add(progressBar, BorderLayout.CENTER);

        playerPanel.add(bottomPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Player", playerPanel);
        tabbedPane.setBackground(new Color(50, 50, 50));
        tabbedPane.setForeground(Color.WHITE);


        // Initialize tabs here (Player, Library, Playlist)

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
