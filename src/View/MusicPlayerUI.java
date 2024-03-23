package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class MusicPlayerUI extends JFrame {
    private JTextField playlistNameField;
    private JButton createPlaylistButton, removePlaylistButton;
    private JComboBox<String> playlistsComboBox;
    private DefaultComboBoxModel<String> playlistsModel;
    private Path musicPath = Paths.get(System.getProperty("user.home"), "Music");
    private Path playlistsPath = musicPath.resolve("Playlists");

    public MusicPlayerUI() {
        setTitle("Music Player Playlist Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        initializeComponents();
        setSize(400, 200);
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        // Ensure the Music directory exists
        if (!Files.exists(musicPath)) {
            try {
                Files.createDirectories(musicPath);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to create Music directory.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit if unable to create the Music directory
            }
        }

        // Ensure the Playlists directory exists inside the Music directory
        if (!Files.exists(playlistsPath)) {
            try {
                Files.createDirectories(playlistsPath);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to create Playlists directory inside Music.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit if unable to create the Playlists directory
            }
        }

        playlistNameField = new JTextField(20);
        createPlaylistButton = new JButton("Create Playlist");
        removePlaylistButton = new JButton("Remove Selected Playlist");
        playlistsModel = new DefaultComboBoxModel<>();
        playlistsComboBox = new JComboBox<>(playlistsModel);

        add(playlistNameField);
        add(createPlaylistButton);
        add(removePlaylistButton);
        add(playlistsComboBox);

        createPlaylistButton.addActionListener(this::createPlaylist);
        removePlaylistButton.addActionListener(this::removeSelectedPlaylist);

        updatePlaylistComboBox();
    }

    private void createPlaylist(ActionEvent e) {
        String playlistName = playlistNameField.getText().trim();
        if (playlistName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Playlist name cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Path newPlaylistPath = playlistsPath.resolve(playlistName);
        if (Files.exists(newPlaylistPath)) {
            JOptionPane.showMessageDialog(this, "Playlist already exists.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Files.createDirectory(newPlaylistPath);
            playlistsModel.addElement(playlistName);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to create new playlist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelectedPlaylist(ActionEvent e) {
        String selectedPlaylist = (String) playlistsComboBox.getSelectedItem();
        if (selectedPlaylist == null) {
            JOptionPane.showMessageDialog(this, "No playlist selected.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Path playlistPath = playlistsPath.resolve(selectedPlaylist);
        try {
            Files.delete(playlistPath);
            playlistsModel.removeElement(selectedPlaylist);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to remove the playlist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePlaylistComboBox() {
        playlistsModel.removeAllElements();
        try {
            Files.list(playlistsPath)
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList())
                    .forEach(playlistsModel::addElement);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to list playlists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MusicPlayerUI().setVisible(true));
    }
}