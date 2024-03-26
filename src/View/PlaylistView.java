package View;

import Factory.MP3Song;
import Factory.WAVSong;
import Model.Playlist;
import Model.Song;
import Observer.PlaylistObserver;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class PlaylistView extends JPanel implements PlaylistObserver
{
    private Playlist playlist;

    private JTextField newPlaylistName;
    private JButton createPlaylistButton;
    private JComboBox<String> playlistsComboBox;
    private JButton removePlaylistButton;
    private JList<String> playlistList;
    private DefaultListModel<String> playlistModel;


    public PlaylistView(Playlist playlist)
    {
        this.playlist = playlist;
        this.playlist.subscribe(this); // Subscribe to playlist updates
        inilializeUI();
        updatePlaylist();
    }

    private void inilializeUI()
    {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));

        playlistModel = new DefaultListModel<>();
        playlistList = new JList<>(playlistModel);
        add(new JScrollPane(playlistList), BorderLayout.CENTER);

        JPanel playlistsManagementPanel = new JPanel();
        playlistsManagementPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playlistsManagementPanel.setBackground(new Color(240, 240, 240));

        newPlaylistName = new JTextField(10);
        playlistsManagementPanel.add(newPlaylistName);

        createPlaylistButton = new JButton("Create Playlist");
        // Action listener to be added here
        playlistsManagementPanel.add(createPlaylistButton);

        playlistsComboBox = new JComboBox<>();
        // Action listener to be added here
        playlistsManagementPanel.add(playlistsComboBox);

        removePlaylistButton = new JButton("Remove Playlist");
        // Action listener to be added here
        playlistsManagementPanel.add(removePlaylistButton);

        add(playlistsManagementPanel, BorderLayout.SOUTH);
    }

    @Override
    public void update()
    {
        SwingUtilities.invokeLater(this::updatePlaylist);

    }

    public void showPlaylist()
    {
        playlistModel.clear();
        for (Song song : playlist.getSongs()) {
            playlistModel.addElement(song.getTitle() + " by " + song.getArtist());
        }
        revalidate();
        repaint();

//        Implementation code to display the playlist in the UI
//        System.out.println("Playlist: " + playlist.getName());
//        for (Song song : playlist.getSongs())
//        {
//            System.out.println(song.getTitle() + "by" + song.getArtist() );
//          }
    }
    public void updatePlaylist()
    {
        showPlaylist();
    }

    public static void main(String[] args)
    {
        // Run the GUI construction in the Event-Dispatching thread for thread-safety.
        SwingUtilities.invokeLater(() -> {
            // Create the main window (a JFrame)
            JFrame frame = new JFrame("Playlist View Test");

            // Create a Playlist and populate it with example data
            Playlist playlist = new Playlist("Favourite");
            playlist.addSong(new MP3Song("Song 1", "Artist A"));
            playlist.addSong(new MP3Song("Song 2", "Artist B"));
            playlist.addSong(new WAVSong("Song 3", "Artist C"));

            // Create an instance of PlaylistView with the playlist
            PlaylistView playlistView = new PlaylistView(playlist);

            // Add the PlaylistView to the main window
            frame.add(playlistView);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300); // Set the size of the main window
            frame.setVisible(true); // Make the window visible
        });
    }

}
