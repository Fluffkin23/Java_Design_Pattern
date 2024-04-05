package View;

import Model.MusicLibrary;
import Model.Song;
import Observer.LibraryObserver;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class LibraryView extends JPanel implements LibraryObserver
{
    private DefaultListModel<String> songListModel; // Model for the list of songs
    private JList<String> songList;
    private JButton refreshButton;
    private JButton removeSongButton;
    private MusicLibrary musicLibrary;

    public LibraryView(MusicLibrary musicLibrary)
    {
        // This line assigns the provided MusicLibrary instance to the musicLibrary field of LibraryView.
        // It stores the reference to the MusicLibrary instance so that LibraryView can access the list of
        // songs and other functionalities provided by MusicLibrary.
        this.musicLibrary = musicLibrary;
        musicLibrary.loadSongsFromDirectory();

        //initializeGUI method to set up the graphical user interface components of LibraryView.
        initializeGUI();
        this.refreshButton.addActionListener(e -> refreshLibrary());
        this.removeSongButton.addActionListener(e -> removeSelectedSong());

        // Registers LibraryView as an observer of the MusicLibrary.
        // This means that LibraryView has told MusicLibrary it wants to be notified whenever there are changes
        // in the music library (e.g., when new songs are loaded).
        musicLibrary.subscribe(this);
    }

    public MusicLibrary getMusicLibrary()
    {
        return musicLibrary;
    }

    public void setMusicLibrary(MusicLibrary musicLibrary)
    {
        this.musicLibrary = musicLibrary;
    }

    public void refreshLibrary()
    {
        this.musicLibrary.loadSongsFromDirectory();
    }

    public void initializeGUI()
    {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));

        // Initialize the song list model and song list
        songListModel = new DefaultListModel<>();
        songList = new JList<>(songListModel);
        add(new JScrollPane(songList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(240, 240, 240));

//--------------------------Refresh Button------------------------------------------------------------------------------
        // Initialize the refresh button
        refreshButton = new JButton("Refresh Library");
        buttonPanel.add(refreshButton); // Add the refresh button to the button panel

        //Initialize the remove song button
        removeSongButton = new JButton("Remove a selected Song");
        buttonPanel.add(removeSongButton); // Add the remove song button to the button panel

        //Add the button panel to the south of the LibraryView
        add(buttonPanel,BorderLayout.SOUTH);

    }

    @Override
    public void update(List<Song> songs)
    {
        songListModel.clear();
        // Iterate over the songs list and add each song's title (or any other detail you wish) to the model
        for(Song song : songs)
        {
            songListModel.addElement(song.getTitle() + " - " + song.getArtist());
        }
    }

    public void removeSelectedSong()
    {
        int selectedIndex = this.songList.getSelectedIndex();
        if (selectedIndex != -1) {
            // Assuming getElementAt returns the song name, not the filePath
            String songName = songListModel.getElementAt(selectedIndex);
            System.out.println("Song Name = " + songName);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this song?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION)
            {
                String musicDirectoryPath = System.getProperty("user.home") + File.separator + "Music";
                File musicDirectory = new File(musicDirectoryPath);
                File[] files = musicDirectory.listFiles();
                //----------------Testing Purpose ------------------------
                if (files != null)
                {
                    System.out.println("Files in Music Directory:");
                    for (File file : files)
                    {
                        System.out.println(file.getName());
                    }
                }
                //--------------------------------------------------------------
                    File[] matchingFiles = musicDirectory.listFiles((dir, name) -> name.startsWith(songName));
                    if (matchingFiles != null && matchingFiles.length > 0) {
                        File fileToDelete = matchingFiles[0]; // Assuming the first match is the correct file
                        System.out.println("File to delete: " + fileToDelete.getPath());
                        if (fileToDelete.delete())
                        {
                            musicLibrary.removeSongFromLibraryByFilePath(fileToDelete.getPath());
                            songListModel.remove(selectedIndex);
                            JOptionPane.showMessageDialog(this, "Song deleted successfully.",
                                    "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this,
                                    "Failed to delete the song file. It may be in use or protected.",
                                    "Deletion Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this,
                                "The song file could not be found in the Music directory.",
                                "File Not Found", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        else
        {
            System.out.println("No song selected.");
        }
    }
}