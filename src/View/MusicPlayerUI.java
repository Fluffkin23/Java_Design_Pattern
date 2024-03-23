package View;

import Model.MusicLibrary;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class MusicPlayerUI extends JFrame {
    private MusicLibrary musicLibrary;
    private LibraryView libraryView;

    public MusicPlayerUI() {
        setTitle("Music Player");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        musicLibrary = new MusicLibrary(); // Initialize your music library
        libraryView = new LibraryView(musicLibrary); // Initialize your library view

        JButton createSongButton = new JButton("Create Song");
        createSongButton.addActionListener(e -> openCreateSongUI());

        JButton showLibraryButton = new JButton("Show Library");
        showLibraryButton.addActionListener(e -> libraryView.showLibrary());

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(createSongButton);
        panel.add(showLibraryButton);

        add(panel, BorderLayout.SOUTH);

        // Optionally add the library view to the main UI
        add(new JScrollPane(libraryView.getTable()), BorderLayout.CENTER);

    }

    private void openCreateSongUI() {
        CreateSong createSongDialog = new CreateSong(musicLibrary);
        createSongDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MusicPlayerUI().setVisible(true));
    }
}
