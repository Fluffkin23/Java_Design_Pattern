package View;

import Controller.MusicController;
import Model.Playlist;
import Model.Song;
import Observer.PlaylistObserver;

import javax.swing.*;
import java.awt.*;


public class PlaylistView extends JPanel implements PlaylistObserver
{
    private Playlist playlist;

    private JTextField newPlaylistName;
    private JButton createPlaylistButton;
    private JComboBox<String> playlistsComboBox;
    private JButton removePlaylistButton;
    private JButton refreshButton;
    private JList<String> playlistList;
    private DefaultListModel<String> playlistModel;
    private MusicController musicController;


    public PlaylistView(MusicController controller, Playlist playlist)
    {
        this.musicController = controller;
        if (playlist != null)
        {
            this.playlist = playlist;
            this.playlist.subscribe(this); // Subscribe to playlist updates
        }
        else
        {
            // Handle the case where playlist is null, maybe initialize with a default or empty playlist
            this.playlist = new Playlist("New Playlist"); // This could be a default or temporary solution
            // Don't forget to add it to the manager if necessary
        }
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

        createPlaylistButton.addActionListener(e ->
        {
            String newPlaylistName = JOptionPane.showInputDialog("Enter new playlist name:");
            if (newPlaylistName != null && !newPlaylistName.trim().isEmpty())
            {
                Playlist newPlaylist = musicController.createPlaylist(newPlaylistName.trim());
                if (newPlaylist != null)
                {
                    playlistsComboBox.addItem(newPlaylistName.trim());
                    playlistsComboBox.setSelectedItem(newPlaylistName.trim());
                    setPlaylist(newPlaylist);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "A playlist with this name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        playlistsComboBox = new JComboBox<>();
        // Action listener to be added here
        playlistsManagementPanel.add(playlistsComboBox);

        playlistsComboBox.addActionListener(e ->
        {
            String selectedPlaylistName = (String) playlistsComboBox.getSelectedItem();
            Playlist selectedPlaylist = musicController.getPlaylist(selectedPlaylistName);
            setPlaylist(selectedPlaylist);
        });

        removePlaylistButton = new JButton("Remove Playlist");
        // Action listener to be added here
        playlistsManagementPanel.add(removePlaylistButton);

        add(playlistsManagementPanel, BorderLayout.SOUTH);
    }

    public void loadPlaylistNamesIntoComboBox() {
        playlistsComboBox.removeAllItems(); // Clear existing entries if any
        for (String playlistName : musicController.getPlaylistName()) {
            playlistsComboBox.addItem(playlistName);
        }
    }

    public void setPlaylist(Playlist playlist)
    {
        if (this.playlist != null)
        {
            this.playlist.unsubscribe(this);
        }
        this.playlist = playlist;
        if (playlist != null)
        {
            playlist.subscribe(this); // Subscribe to the new playlist
            update(); // Update the view
        }
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
            String songDisplayText = song.getTitle() + " by " + song.getArtist();
            playlistModel.addElement(songDisplayText);
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

//    public static void main(String[] args)
//    {
//        // Run the GUI construction in the Event-Dispatching thread for thread-safety.
//        SwingUtilities.invokeLater(() -> {
//            // Create the main window (a JFrame)
//            JFrame frame = new JFrame("Playlist View Test");
//
//            // Create a Playlist and populate it with example data
//            Playlist playlist = new Playlist("Favourite");
//            playlist.addSong(new MP3Song("Song 1", "Artist A"));
//            playlist.addSong(new MP3Song("Song 2", "Artist B"));
//            playlist.addSong(new WAVSong("Song 3", "Artist C"));
//
//            // Create an instance of PlaylistView with the playlist
//            PlaylistView playlistView = new PlaylistView(playlist);
//
//            // Add the PlaylistView to the main window
//            frame.add(playlistView);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(400, 300); // Set the size of the main window
//            frame.setVisible(true); // Make the window visible
//        });
//    }

}
