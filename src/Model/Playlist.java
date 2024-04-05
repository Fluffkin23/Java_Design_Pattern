package Model;

import Factory.AIFFSongCreator;
import Factory.SongCreator;
import Factory.WAVSongCreator;
import Observer.PlaylistObserver;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Playlist
{
    private List<Song> playlistSongs;
    private String playlistName;
    private List<PlaylistObserver> observers;

    public Playlist(String playlistName)
    {
        this.playlistName = playlistName;
        this.playlistSongs = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public String getPlaylistName()
    {
        return playlistName;
    }

    public void setPlaylistName(String playlistName)
    {
        this.playlistName = playlistName;
    }

    public List<Song> getPlaylistSongs()
    {
        return this.playlistSongs;
    }

    public void setPlaylistSongs(List<Song> playlistSongs)
    {
        this.playlistSongs = playlistSongs;
    }

    public List<PlaylistObserver> getObservers()
    {
        return observers;
    }

    public void setObservers(List<PlaylistObserver> observers)
    {
        this.observers = observers;
    }


// Method to create a folder for the playlist within the user's "Music" directory
    public void createPlaylistFolder()
    {
        // Construct the path to the "Music" directory in the user's home folder
        String musicDirectoryPath = System.getProperty("user.home") + File.separator + "Music";

        // Create a File object representing the directory for this specific playlist within the "Music" directory
        // The name of the directory is the same as the name of the playlist
        File playlistDirectory = new File(musicDirectoryPath, this.playlistName);

        // Create a File object representing the directory for this specific playlist within the "Music" directory
        // The name of the directory is the same as the name of the playlist
        if(!playlistDirectory.exists())
        {
            // Attempt to create the directory (and any necessary parent directories)
            boolean created = playlistDirectory.mkdirs();
            if(!created)
            {
                // If the directory could not be created, print an error message to the console
                System.out.println("Failed to create playlist directory:" + playlistDirectory.getPath());
            }
        }
    }

    //Add Songs to Playlist and Directory
    public void addSongToPlaylist(Song song)
    {
        // Add the song object to the playlist
        this.playlistSongs.add(song);
        //Ensure the playlist folder exist
        createPlaylistFolder();
        // Converts the song's file path (a String) to a Path object. This is the path to the original song file.
        Path sourcePath = Path.of(song.getFilePath());
        // Constructs a string representing the path to the Music directory within the user's home directory.
        String musicDirectoryPath = System.getProperty("user.home") + File.separator + "Music";
        // Creates a destination Path object pointing to where the song should be stored within the playlist folder.
        // The playlist folder is named after the playlist and resides within the Music directory.
        // sourcePath.getFileName().toString() ensures that the destination file has the same name as the source file.
        Path destinationPath = Path.of(musicDirectoryPath,this.playlistName,sourcePath.getFileName().toString());
        try
        {
            //Copy the song file to the playlist directory
            Files.copy(sourcePath,destinationPath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e )
        {
            e.printStackTrace();
        }
        notifyObservers(); // Notify observers of the change
    }

    public void loadSongs()
    {
        String musicFolderPath = System.getProperty("user.home") + File.separator + "Music";
        File playlistFolder = new File(musicFolderPath, playlistName);

        File[] files = playlistFolder.listFiles((dir, name) -> name.endsWith(".aiff") || name.endsWith(".wav"));

        // Check if the files array is not null, indicating that listing was successful
        if(files != null) {
            // Clear the existing list of songs to refresh the library
            this.playlistSongs.clear();
            // Iterate through each file found in the directory
            for (File file : files) {
                // Obtain the file's name
                String fileName = file.getName();

                // Extract the base name of the file by removing the extension.
                String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                // Attempt to split the base name into two parts, assuming "Title-Artist" format
                String[] parts = baseName.split(" - ", 2);
                // Assign the first part as the song title or default to "Unknown Title" if split fails
                String songTitle = parts.length > 1 ? parts[0] : "Unknown Title";
                // Assign the second part as the artist name or default to "Unknown Artist" if split fails
                String artistName = parts.length > 1 ? parts[1] : "Unknown Artist";
                // Store the full path of the file
                String filePath = file.getAbsolutePath();

                // Select the correct songCreator based on the file type
                SongCreator songCreator;
                if (fileName.endsWith(".aiff")) {
                    songCreator = new AIFFSongCreator();
                } else if (fileName.endsWith(".wav")) {
                    songCreator = new WAVSongCreator();
                } else {
                    JOptionPane.showMessageDialog(null, "Unsupported file type.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Use the songCreator to create a new Song object ( Factory Method)
                Song song = songCreator.createSong(songTitle, artistName, filePath);
                // Add the newly created song to the library
                addSongToPlaylist(song);
            }
            // Notify all subscribers that the library has been updated
            notifyObservers();
            // For debugging, print the current list of songs to the console
            System.out.println(getPlaylistSongs());
        }
    }

    public void loadPlaylistFromDirectory(String directoryPath)
    {
        File directory = new File(directoryPath);

        // Filename filter to accept only .wav and .aiff files
        FilenameFilter audioFilter = (dir, name) -> name.endsWith(".wav") || name.endsWith(".aiff");
        File[] files = directory.listFiles(audioFilter);

        // Check if the files array is not null, indicating that listing was successful
        if(files != null) {
            // Clear the existing list of songs to refresh the library
            this.playlistSongs.clear();
            // Iterate through each file found in the directory
            for (File file : files) {
                // Obtain the file's name
                String fileName = file.getName();

                // Extract the base name of the file by removing the extension.
                String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                // Attempt to split the base name into two parts, assuming "Title-Artist" format
                String[] parts = baseName.split(" - ", 2);
                // Assign the first part as the song title or default to "Unknown Title" if split fails
                String songTitle = parts.length > 1 ? parts[0] : "Unknown Title";
                // Assign the second part as the artist name or default to "Unknown Artist" if split fails
                String artistName = parts.length > 1 ? parts[1] : "Unknown Artist";
                // Store the full path of the file
                String filePath = file.getAbsolutePath();

                // Select the correct songCreator based on the file type
                SongCreator songCreator;
                if (fileName.endsWith(".aiff")) {
                    songCreator = new AIFFSongCreator();
                } else if (fileName.endsWith(".wav")) {
                    songCreator = new WAVSongCreator();
                } else {
                    JOptionPane.showMessageDialog(null, "Unsupported file type.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Use the songCreator to create a new Song object ( Factory Method)
                Song song = songCreator.createSong(songTitle, artistName, filePath);
                // Add the newly created song to the library
                addSongToPlaylist(song);
            }
            // Notify all subscribers that the library has been updated
            notifyObservers();
            // For debugging, print the current list of songs to the console
            System.out.println(getPlaylistSongs());
        }
    }

    public void subscribe(PlaylistObserver observer)
    {
        this.observers.add(observer);
    }

    public void unsubscribe(PlaylistObserver observer)
    {
        observers.remove(observer);
    }

    private void notifyObservers()
    {
        for (PlaylistObserver observer : observers)
        {
            observer.update();
        }
    }
}