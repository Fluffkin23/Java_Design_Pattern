package View;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PlaylistView extends JPanel
{
    private JTextField newPlaylistName;
    private JButton createPlaylistButton;
    private JComboBox<String> playlistsComboBox;
    private JButton removePlaylistButton;
    public PlaylistView()
    {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));

        JList<String> playlistList = new JList<>(new String[]
                {
                        "Playlist 1", "Playlist 2"
                });
        add(new JScrollPane(playlistList), BorderLayout.CENTER);

        JPanel playlistsManagementPanel = new JPanel();
        playlistsManagementPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playlistsManagementPanel.setBackground(new Color(240, 240, 240));

        // Text field for new playlist name
        newPlaylistName = new JTextField(10);
        playlistsManagementPanel.add(newPlaylistName);

        // "Create Playlist" button
        createPlaylistButton = new JButton("Create Playlist");
        // Temporarily remove the action listener for backend integration
        playlistsManagementPanel.add(createPlaylistButton);

        // Combo box for existing playlists
        playlistsComboBox = new JComboBox<>();

        // Temporarily populate with placeholder data
        playlistsComboBox.addItem("Example Playlist 1");
        playlistsComboBox.addItem("Example Playlist 2");
        playlistsManagementPanel.add(playlistsComboBox);

        // "Remove Playlist" button
        removePlaylistButton = new JButton("Remove Playlist");

        // Temporarily remove the action listener for backend integration
        playlistsManagementPanel.add(removePlaylistButton);

        // Add the management panel to the bottom of the PlaylistView
        add(playlistsManagementPanel, BorderLayout.SOUTH);
    }
}
