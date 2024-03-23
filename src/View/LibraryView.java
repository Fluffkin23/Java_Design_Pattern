package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Model.MusicLibrary;
import Model.Song;
import Observer.LibraryObserver;

public class LibraryView implements LibraryObserver {
    private MusicLibrary library;
    private JTable table;
    private DefaultTableModel tableModel;

    public LibraryView(MusicLibrary library) {
        this.library = library;
        this.library.subscribe(this); // Subscribe to the library for updates

        // Initialize the table with column names
        tableModel = new DefaultTableModel(new Object[]{"Title", "Artist"}, 0);
        table = new JTable(tableModel);

        // Populate the table with songs
        update();
    }

    @Override
    public void update() {
        // Clear the existing rows
        tableModel.setRowCount(0);

        // Add rows to the table model with song details
        for (Song song : library.getSongs()) {
            tableModel.addRow(new Object[]{song.getTitle(), song.getArtist()});
        }
    }

    public void showLibrary() {
        JFrame frame = new JFrame("Music Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
    }
}
