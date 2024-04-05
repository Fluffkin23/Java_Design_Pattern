package View;

import Controller.MusicController;
import Model.Playlist;
import Model.Song;
import Observer.MusicControllerObserver;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class MusicPlayerUI extends JFrame implements MusicControllerObserver {
    private MusicController musicController;
    private JLabel trackTitleLabel;
    private JLabel trackInfoLabel;
    private JButton loadPlaylist;

    public MusicPlayerUI(MusicController musicController) {
        this.musicController = musicController;
        musicController.subscribe(this);
        initializeGUI();
    }

    public void initializeGUI() {
        // Set up the main window
        setTitle("The Best Music Player");
        setSize(1200, 800); // Set the size of the app
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
        trackTitleLabel = new JLabel("Track Title", SwingConstants.CENTER);


        gbc.gridy = 1; // Move to the next row
        gbc.anchor = GridBagConstraints.CENTER; // Center the component
        infoPanel.add(trackTitleLabel, gbc);

        // Add artist and album name with horizontal centering
        trackInfoLabel = new JLabel("Track info: Artist", SwingConstants.CENTER);
        gbc.gridy = 2; // Move to the next row
        infoPanel.add(trackInfoLabel, gbc);

        // Playback controls
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        controlsPanel.setBackground(new Color(0, 0, 0));

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> musicController.toggleShufflePlayback());
        controlsPanel.add(shuffleButton);

        JButton refreshbutton = new JButton("Refresh");
        refreshbutton.addActionListener(e -> musicController.notifySubscribers());
        controlsPanel.add(refreshbutton);


        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(e -> musicController.previousSong());
        controlsPanel.add(previousButton);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> musicController.nextSong());
        controlsPanel.add(nextButton);

        JButton repeatButton = new JButton("Repeat");
        repeatButton.addActionListener(e -> musicController.toggleRepeatPlayblack());
        controlsPanel.add(repeatButton);

        loadPlaylist = new JButton("Load Playlist");
        controlsPanel.add(loadPlaylist);
        loadPlaylist.addActionListener(e -> selectAndLoadPlaylist());

        JButton btnPlay = new JButton("Play2");
        btnPlay.addActionListener(e -> musicController.playSong());
        controlsPanel.add(btnPlay);

        JButton psbutton = new JButton("pause");
        psbutton.addActionListener(e -> musicController.pauseSong());
        controlsPanel.add(psbutton);

        JButton resumeButton = new JButton("resume");
        resumeButton.addActionListener(e -> musicController.resumeSong());
        controlsPanel.add(resumeButton);

        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50); // 0 to 100% volume
        volumeSlider.addChangeListener(e -> {
            if (!volumeSlider.getValueIsAdjusting()) {
                int volumePercentage = volumeSlider.getValue();
                musicController.changeVolume(volumePercentage);
            }
        });

        // Adding components to playerPanel
        playerPanel.add(infoPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(255, 255, 255));
        bottomPanel.add(controlsPanel, BorderLayout.NORTH);
        bottomPanel.add(volumeSlider, BorderLayout.CENTER);

        playerPanel.add(bottomPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Player", playerPanel);
        tabbedPane.setBackground(new Color(50, 50, 50));
        tabbedPane.setForeground(Color.WHITE);

        CreateSongView createSongView = new CreateSongView();
        tabbedPane.addTab("Add Song", createSongView);

        LibraryView libraryView = new LibraryView(musicController.getMusicLibrary());
        tabbedPane.addTab("Music Library", libraryView);

        PlaylistView playlistView = new PlaylistView(musicController.getPlaylistLibrary());
        tabbedPane.addTab("Playlists", playlistView);


        // Adding the tabbed pane to the frame
        add(tabbedPane);
    } // end of the final GUI

    public void selectAndLoadPlaylist()
    {
        // Construct the path to the Music Folder in the user's home directory
        String userHome = System.getProperty("user.home");
        File musicFolder = new File(userHome, "Music");

        // Initialize JFileChooser with the musicFolder as the current directory if it exists and is a directory
        JFileChooser chooser;
        if (musicFolder.exists() && musicFolder.isDirectory()) {
            chooser = new JFileChooser(musicFolder);
        } else {
            System.out.println("The Music Folder does not exist or is not a directory. Please ensure the Music Folder is correctly located in your home directory.");
            return;
        }

        // Configure the chooser
        chooser.setDialogTitle("Select Playlist Folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        // Show the open dialog window
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = chooser.getSelectedFile();
            // Load the playlist from the selected folder
            Playlist playlist = new Playlist(selectedFolder.getName());
            playlist.loadPlaylistFromDirectory(selectedFolder.getAbsolutePath());
            musicController.setPlaylist(playlist);

            // Debugging output to check loaded songs
            for (Song song : musicController.getPlaylist().getPlaylistSongs()) {
                System.out.println("Index: " + musicController.getPlaylist().getPlaylistSongs().indexOf(song) + " - Title: " + song.getTitle());
            }
        } else {
            System.out.println("No selection was made.");
        }
    }

    @Override
    public void update(String title, String artist)
    {
        trackTitleLabel.setText(title);
        trackInfoLabel.setText("Track info: " + artist);
    }

    public static void main(String[] args) {
        MusicController musicController1 = new MusicController();



        SwingUtilities.invokeLater(() -> {
            MusicPlayerUI app = new MusicPlayerUI(musicController1);
            app.setVisible(true);
        });
    }
}