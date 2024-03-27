package View;

import Controller.MusicController;
import Factory.MP3Song;
import Factory.WAVSong;
import Model.MusicLibrary;
import Model.Song;
import Observer.LibraryObserver;

import javax.swing.*;
import java.awt.*;

public class LibraryView extends JPanel implements LibraryObserver
{
    private MusicLibrary library;
    private JButton addButton;
    private JButton refreshButton;
    private JComboBox<String> playlistsComboBox;
    private DefaultListModel<String> songListModel; // Model for the list of songs
    private JList<String> songList; // View for the list of songs
    private MusicController musicController;

    public LibraryView(MusicController musicController)
    {
        this.musicController = musicController;
        this.musicController.getMusicLibrary().subscribe(this);
        initializeUI();

    }

    public void initializeUI()
    {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));

        // Initialize the song list model and song list
        songListModel = new DefaultListModel<>();
        songList = new JList<>(songListModel);
        add(new JScrollPane(songList), BorderLayout.CENTER);

        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPanel.setBackground(new Color(240, 240, 240));

        // Initialize and add the "Add to Playlist" button
        addButton = new JButton("Add to Playlist");
        addPanel.add(addButton);

        addButton.addActionListener(e ->
        {
            String selectedSongTitle = songList.getSelectedValue();
            if (selectedSongTitle != null && playlistsComboBox.getSelectedItem() != null) {
                String playlistName = (String) playlistsComboBox.getSelectedItem();
                Song selectedSong = musicController.getMusicLibrary().getSongs().stream()
                        .filter(song -> (song.getTitle() + " by " + song.getArtist()).equals(selectedSongTitle))
                        .findFirst().orElse(null);

                if (selectedSong != null) {
                    musicController.addSongToPlaylist(selectedSong, playlistName);
                } else {
                    JOptionPane.showMessageDialog(this, "Song not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        // Initialize and add the combo box for selecting playlists
        playlistsComboBox = new JComboBox<>();
        addPanel.add(playlistsComboBox);

        playlistsComboBox.removeAllItems(); // Clear existing items
        for (String playlistName : musicController.getPlaylistName()) {
            playlistsComboBox.addItem(playlistName);
        }




        refreshButton = new JButton("Refresh Library");
        refreshButton.addActionListener(e -> musicController.getMusicLibrary().loadSongsFromFolder());
        addPanel.add(refreshButton);



        // Add the addPanel to the bottom of the LibraryView
        add(addPanel, BorderLayout.SOUTH);

        updateLibrary(); // Initial update to populate the list
    }

    //This library is called when the library is updated
    @Override
    public void update()
    {
        System.out.println("LibraryView is being updated.");
        updateLibrary();
    }

    // Call this method to show the library in the UI
    public void showLibrary()
    {
        songListModel.clear(); // Clear the existing list
        for (Song song : musicController.getMusicLibrary().getSongs())
        {
            songListModel.addElement(song.getTitle() + " by " + song.getArtist());
        }

//        System.out.println("Displaying updated library:");
//        // Display the library in some way
//        for (Song song : library.getSongs())
//        {
//            System.out.println(song.getDetails());
//        }
    }

    // Call this method to update the library in the GUI
    public void updateLibrary()
    {
        // This method might be responsible for fetching new data from the library
        // and updating the view accordingly.
        showLibrary(); //
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
    private static void createAndShowGUI() {
        // Create the main window (JFrame) for the application
        JFrame frame = new JFrame("Library View Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a MusicLibrary and populate it with some songs

        MusicController musicController1 = new MusicController();
        musicController1.addSongLibrary(new MP3Song("Song 1", "Artist A",""));
        musicController1.addSongLibrary(new MP3Song("Song 2", "Artist B",""));
        musicController1.addSongLibrary(new WAVSong("Song 3", "Artist C",""));
        // Assume MusicLibrary has an addSong() method

        // Create the LibraryView, passing in the music library
        LibraryView libraryView = new LibraryView(musicController1);

        // Add the LibraryView to the JFrame
        frame.add(libraryView);

        // Set the size, and then display the JFrame
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

}
