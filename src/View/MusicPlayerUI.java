package View;

import javax.swing.*;
import java.awt.*;

public class MusicPlayerUI extends JFrame {

    public MusicPlayerUI()
    {
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

        LibraryView libraryView = new LibraryView();
        tabbedPane.addTab("Library", libraryView);

        PlaylistView playlistView = new PlaylistView();
        tabbedPane.addTab("Playlist", playlistView);

        // Adding the tabbed pane to the frame
        add(tabbedPane);
    }

    // for testing GUI
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            MusicPlayerUI app = new MusicPlayerUI();
            app.setVisible(true);
        });
    }
}
