package View;

import Factory.AIFFSong;
import Factory.WAVSong;
import Model.Playlist;
import Model.PlaylistLibrary;
import Model.Song;
import Observer.PlaylistObserver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class PlaylistView extends JPanel implements PlaylistObserver
{
    private Playlist playlist;
    private PlaylistLibrary playlistLibrary;
    private JList<String> songList; // List to display song
    private DefaultListModel<String> songListModel; // Model for the songlist
    private JList<String> playlistList; // List to display all playlist
    private DefaultListModel<String> playlistListModel; // Model for playlist
    private JButton createPlaylistButton;
    private JButton removePlaylistButton;
    private JButton getRemoveSongFromPlaylist;
    private JButton addSongToPlaylistButton;
    private JButton removeSongFromPlaylistButton;
    private JButton refreshPlaylistList;
    private JButton displaySong;

    public PlaylistView(PlaylistLibrary playlistLibrary)
    {
        this.playlistLibrary = playlistLibrary;
        initializeGUI(); // Ensure this is called before any UI updates
        showPlaylistsList();

    }

    public void initializeGUI()
    {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        songListModel = new DefaultListModel<>();
        songList = new JList<>(songListModel);

        playlistListModel = new DefaultListModel<>();
        playlistList = new JList<>(playlistListModel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(songList),
                new JScrollPane(playlistList));
        splitPane.setDividerLocation(150); // Adjust this as needed

        add(splitPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        createPlaylistButton = new JButton("Create Playlist");
        removePlaylistButton = new JButton("Remove Playlist");
        addSongToPlaylistButton = new JButton("Add Song to Playlist");
        removeSongFromPlaylistButton = new JButton("Remove Song From Playlist");
        refreshPlaylistList = new JButton("Refres List");
        displaySong = new JButton("Display songs");
        removeSongFromPlaylistButton = new JButton("Remove Selected Song");

        // Add buttons to the panel
        buttonPanel.add(createPlaylistButton);
        buttonPanel.add(removePlaylistButton);
        buttonPanel.add(addSongToPlaylistButton);
        buttonPanel.add(removeSongFromPlaylistButton);
        buttonPanel.add(refreshPlaylistList);
        buttonPanel.add(displaySong);
        buttonPanel.add(removeSongFromPlaylistButton);


        // Add button panel to the bottom
        add(buttonPanel, BorderLayout.SOUTH);

        createPlaylistButton.addActionListener(e -> createNewPlaylist());
        refreshPlaylistList.addActionListener(e -> playlistLibrary.getPlaylistList());
        removePlaylistButton.addActionListener(e -> removeSelectedPlaylist());
        addSongToPlaylistButton.addActionListener(e -> addSongToPlaylist());
        displaySong.addActionListener(e -> displaySongsForSelectedPlaylist());
    }

    public Playlist getPlaylist()
    {
        return playlist;
    }

    public void setPlaylist(Playlist playlist)
    {
        this.playlist = playlist;
    }

    public DefaultListModel<String> getSongListModel()
    {
        return songListModel;
    }

    public void setSongListModel(DefaultListModel<String> songListModel)
    {
        this.songListModel = songListModel;
    }

    public JList<String> getSongList()
    {
        return songList;
    }

    public void setSongList(JList<String> songList)
    {
        this.songList = songList;
    }

    // Method to prompt the user to create a new playlist
    public void createNewPlaylist()
    {
        // Show an input dialog that asks the user for the name of the new playlist.
        // Store the user's input in the variable 'playlistName'.
        String playlistName = JOptionPane.showInputDialog(this,"Enter playlist name",
                "Create Playlist", JOptionPane.PLAIN_MESSAGE);

        // Check if the user entered a name (ensuring it's not null and not just whitespace)
        if(playlistName != null && !playlistName.trim().isEmpty())
        {
            // Create a new Playlist object with the entered name
            Playlist newPlaylist = new Playlist(playlistName);

            // Call the method to create a directory for the new playlist
            // This is assuming that each playlist corresponds to a directory in the filesystem
            newPlaylist.createPlaylistFolder();
           // playlistListModel.addElement(playlistName);

            // Set the newly created playlist as the selected value in the JList,
            // making it the current focus in the UI.
            playlistList.setSelectedValue(playlistName,true);
            System.out.println(this.playlistLibrary);
            this.playlistLibrary.addPlaylistToLibrary(newPlaylist);
            update();
        }
    }

    public void removeSelectedPlaylist()
    {
        // Get the selected playlist name from the JList
        String selectedPlaylistName = playlistList.getSelectedValue();
        System.out.println(selectedPlaylistName);
        if(selectedPlaylistName == null)
        {
            JOptionPane.showMessageDialog(this, "Please select a playlist to remove.",
                    "No Playlist Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirm deletion with the user
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the playlist: "
                + selectedPlaylistName + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION)
        {
            // Attempt to remove the playlist from the PlaylistLibrary
            boolean success = playlistLibrary.removePlaylist(selectedPlaylistName);
            if (success)
            {
                // If successfully removed, update the UI
                playlistListModel.removeElement(selectedPlaylistName);
                JOptionPane.showMessageDialog(this, "Playlist removed successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                songListModel.clear();
            }
            else
            {
                // If the removal was unsuccessful, show an error message
                JOptionPane.showMessageDialog(this, "Failed to remove the playlist.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void addSongToPlaylist()
    {
        String userHome = System.getProperty("user.home");
        File musicFolder = new File(userHome, "Music");
        // Retrieve the name of the currently selected playlist from the playlist JList.
        String selectedPlaylistName = playlistList.getSelectedValue();
        // Create a new Playlist object with the selected name.
        Playlist selectedPlaylist = playlistLibrary.getPlaylistByName(selectedPlaylistName);
        // Debugging print statement to confirm the name of the newly created or selected playlist.
        System.out.println(selectedPlaylist.getPlaylistName());

        // Create a JFileChooser dialog to allow the user to select a song file.
        JFileChooser fileChooser = new JFileChooser();
        if (musicFolder.exists() && musicFolder.isDirectory()) {
            fileChooser.setCurrentDirectory(musicFolder);
        } else {
            JOptionPane.showMessageDialog(this, "The Music folder does not exist in the home directory. Please ensure the Music folder is correctly located.", "Folder Not Found", JOptionPane.ERROR_MESSAGE);
            return;
        }
        fileChooser.setDialogTitle("Select a Song");
        // Set a file filter to only show .wav and .aiff files, common audio formats.
        fileChooser.setFileFilter(new FileNameExtensionFilter("Audio Files", "wav", "aiff"));
        // Show the file chooser dialog and capture the user's selection action.
        int userSelection = fileChooser.showOpenDialog(this);
        // Check if the user approved a file selection.
        if(userSelection == JFileChooser.APPROVE_OPTION)
        {
            // Retrieve the selected file.
            File selectedFile = fileChooser.getSelectedFile();
            // Splitting the file name
            String fileName = selectedFile.getName();
            String[] parts = fileName.split(" - ", 2);
            // Check if the file name doesn't match the expected format (i.e., doesn't contain " - ").
            if (parts.length < 2) {
                JOptionPane.showMessageDialog(this, "The file name does not follow the expected " +
                        "format: Title - artist.extension", "Format Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method to prevent further processing.
            }

            String title = parts[0];
            String artistAndExtension = parts[1];

            // Find the last occurrence of '.' to separate the artist name from the file extension.
            int lastDotIndex = artistAndExtension.lastIndexOf('.');
            if (lastDotIndex == -1) {
                JOptionPane.showMessageDialog(this, "Unable to find the file extension.", "Format Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Extract the artist name from the string, assuming everything before the last '.'.
            String artistName = artistAndExtension.substring(0, lastDotIndex);
            // Extract the file extension from the string, assuming everything after the last '.'.
            String extension = artistAndExtension.substring(lastDotIndex + 1);
            // Check the file extension to determine the type of Song object to create.
            if(extension.equals(".wav"))
            {
                // Create a WAVSong object for .wav files.
                Song newSong = new WAVSong(title,artistName,selectedFile.getAbsolutePath());
                selectedPlaylist.addSongToPlaylist(newSong);
            }
            else
            {
                // Create an AIFFSong object for other files, assuming .aiff by default.
                Song newSong = new AIFFSong(title,artistName,selectedFile.getAbsolutePath());
                selectedPlaylist.addSongToPlaylist(newSong);
            }
        }

    }

    public Playlist displaySongsForSelectedPlaylist()
    {
        Playlist selectedPlaylist = playlistLibrary.getPlaylistByName(playlistList.getSelectedValue());
        if (selectedPlaylist != null)
        {
            selectedPlaylist.subscribe(this);
            selectedPlaylist.loadSongs(); // Load songs from the folder
            songListModel.clear(); // Clear existing songs in the model
            for (Song song : selectedPlaylist.getPlaylistSongs()) {
                songListModel.addElement(song.getFileName()); // Assuming Song has a getName method
                System.out.println(song.getFileName());
            }
        }
        return selectedPlaylist;
    }

    // Method to display the list of playlist in the playlist model Jlist
    public void showPlaylistsList()
    {
        // First, clear the playlistListModel to remove any existing entries.
        // This ensures that everything starting with a clean slate before adding new playlists.
        playlistListModel.clear();
        // Call the loadPlaylistsFromMusicFolder method on playlistLibrary to retrieve an updated list of Playlist objects.
        // This method  reads from the filesystem to dynamically get the list of all playlists.
        for(Playlist playlist : playlistLibrary.loadPlaylistsFromMusicFolder())
        {
            playlistListModel.addElement(playlist.getPlaylistName());
        }
        // After this loop, the playlistListModel will contain the names of all playlists currently recognized by the system,
        // and these names will be displayed in the UI component (JList) that uses playlistListModel.
    }

    @Override
    public void update()
    {
        playlistListModel.clear(); // Clear the existing list
        for (Playlist playlist : playlistLibrary.getPlaylistList())
        {
            playlistListModel.addElement(playlist.getPlaylistName()); // Repopulate the list
    }
    }
}
