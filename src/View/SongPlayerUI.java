package View;

import Model.Song;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SongPlayerUI extends JFrame {
    private SongView songView;
    private JLabel songTitleLabel;
    private JLabel songArtistLabel;
    private JLabel songCoverLabel;
    private JButton prevButton, playPauseButton, nextButton, playbackButton, repeatButton, shuffleButton;

    // Path to the music library
    private Path musicLibraryPath = Paths.get(System.getProperty("user.home"), "Music", "Library");

    public SongPlayerUI() {
        setTitle("Song Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        // Song metadata
        songTitleLabel = new JLabel("Song Title");
        songArtistLabel = new JLabel("Artist Name");

        // Cover image
        songCoverLabel = new JLabel();
        updateSongCover(null); // Pass null or path to default cover if no song is selected

        songView = new SongView();
        add(songView, BorderLayout.EAST);

        // Playback controls
        JPanel controlsPanel = new JPanel(new FlowLayout());
        prevButton = new JButton("Prev");
        playPauseButton = new JButton("Play/Pause");
        nextButton = new JButton("Next");
        playbackButton = new JButton("Playback Mode");
        repeatButton = new JButton("Repeat");
        shuffleButton = new JButton("Shuffle");

        controlsPanel.add(prevButton);
        controlsPanel.add(playPauseButton);
        controlsPanel.add(nextButton);
        controlsPanel.add(playbackButton);
        controlsPanel.add(repeatButton);
        controlsPanel.add(shuffleButton);

        // Adding components to the frame
        setLayout(new BorderLayout());
        add(songTitleLabel, BorderLayout.NORTH);
        add(songCoverLabel, BorderLayout.CENTER);
        add(controlsPanel, BorderLayout.SOUTH);

    }

    private void updateSongCover(Path coverImagePath) {
        ImageIcon coverIcon;
        if (coverImagePath != null && Files.exists(coverImagePath)) {
            coverIcon = new ImageIcon(coverImagePath.toUri().toString());
        } else {
            // Load default cover image from classpath
            java.net.URL imgUrl = getClass().getResource("/img/song sample picture.png");
            if (imgUrl != null) {
                coverIcon = new ImageIcon(imgUrl);
            } else {
                System.err.println("Default cover image not found.");
                coverIcon = new ImageIcon(); // This creates an empty icon as a fallback.
            }
        }
        songCoverLabel.setIcon(coverIcon);
    }



    public void updateSongMetadata(String title, String artist, Path coverPath) {
        songTitleLabel.setText(title);
        songArtistLabel.setText(artist);
        updateSongCover(coverPath);
    }

    private void setupListeners() {
        // Placeholder implementations for button actions
        playPauseButton.addActionListener(e -> togglePlayPause());
        nextButton.addActionListener(e -> nextSong());
        prevButton.addActionListener(e -> previousSong());
    }

    public void updateSongView(Song currentSong) {
        songView.updateSong(currentSong);
    }

    // Placeholder methods for functionalities to be implemented later
    private void togglePlayPause() {}
    private void nextSong() {}
    private void previousSong() {}

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new SongPlayerUI().setVisible(true);
        });
    }
}
