package View;

import javax.swing.*;
import java.awt.*;

public class LibraryView extends JPanel
{
    private JButton addButton;
    private JComboBox<String> playlistsComboBox;
    public LibraryView()
    {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));

        JList<String> songList = new JList<>(new String[]
                {
                        "Song 1", "Song 2", "Song 3"
                });
        add(new JScrollPane(songList), BorderLayout.CENTER);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addPanel.setBackground(new Color(240, 240, 240));

        // Button to add selected song to playlist
        addButton = new JButton("Add to Playlist");
        addPanel.add(addButton);

        // Combo box for selecting playlist
        playlistsComboBox = new JComboBox<>();

        // Assume a method to populate this comboBox with playlists from PlaylistView
        addPanel.add(playlistsComboBox);

        // Add the add panel to the bottom of the LibraryView
        add(addPanel, BorderLayout.SOUTH);
    }
}
