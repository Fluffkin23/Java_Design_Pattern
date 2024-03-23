package View;

import Model.Song;

import javax.swing.*;
import java.awt.*;

public class SongView extends JPanel {
    private JTextArea songDetailsArea;
    private JLabel titleLabel;
    private JLabel artistLabel;
    private JTextArea songDetailsTextArea;

    public SongView() {
        setLayout(new BorderLayout()); // Use BorderLayout for layout management
        songDetailsTextArea = new JTextArea("Song details will appear here.");
        songDetailsTextArea.setEditable(false); // Make text area non-editable
        songDetailsTextArea.setLineWrap(true); // Enable line wrap
        songDetailsTextArea.setWrapStyleWord(true); // Wrap lines at word boundaries
        add(new JScrollPane(songDetailsTextArea), BorderLayout.CENTER); // Add text area in a scroll pane for scrolling
    }

    public void updateSong(Song song) {
        // Use the getDetails() method from the Song interface to display song information
        String details = song.getDetails();
        songDetailsTextArea.setText(details); // Update the text area with song details
        // If getDetails() includes structured information like title, artist, etc., parse it accordingly
    }
}
