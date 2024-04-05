package Model;

import Factory.AIFFSongCreator;
import Factory.SongCreator;
import Factory.WAVSongCreator;
import Observer.LibraryObserver;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MusicLibrary
{
    private List<Song> songs;
    private List<LibraryObserver> subscribers;

    public MusicLibrary()
    {
        this.songs = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public List<Song> getSongs()
    {
        return this.songs;
    }

    public void setSongs(List<Song> songs)
    {
        this.songs = songs;
    }

    public List<LibraryObserver> getSubscribers()
    {
        return this.subscribers;
    }

    public void setSubscribers(List<LibraryObserver> subscribers)
    {
        this.subscribers = subscribers;
    }

    public void loadSongsFromDirectory()
    {
        // Creates a File object pointing to the "Music" directory within the user's home directory.
        // System.getProperty("user.home") retrieves the path to the user's home directory,
        // and "Music" specifies the subdirectory named "Music" within that home directory.
        File musicFolder = new File(System.getProperty("user.home"), "Music");

        // Calls the listFiles method on the musicFolder File object to obtain an array of File objects.
        // This array represents all files within the "Music" directory that match the specified filter.
        // The filter, provided as a lambda expression, accepts files ending with ".aiff" or ".wav" extensions.
        // Specifically, (dir, name) -> name.endsWith(".aiff") || name.endsWith(".wav") defines an anonymous
        // FilenameFilter that checks if a file name (name) ends with ".aiff" or ".wav", indicating it's an audio file.
        // The method returns null if musicFolder does not denote a directory, or if an I/O error occurs.
        File[] files = musicFolder.listFiles((dir, name) -> name.endsWith(".aiff") || name.endsWith(".wav"));

        // Check if the files array is not null, indicating that listing was successful
        if(files != null)
        {
            // Clear the existing list of songs to refresh the library
            songs.clear();
            // Iterate through each file found in the directory
            for(File file : files)
            {
                // Obtain the file's name
                String fileName = file.getName();

                // Extract the base name of the file by removing the extension.
                String baseName = fileName.substring(0,fileName.lastIndexOf('.'));
                // Attempt to split the base name into two parts, assuming "Title-Artist" format
                String[] parts = baseName.split(" - ",2);
                // Assign the first part as the song title or default to "Unknown Title" if split fails
                String songTitle = parts.length > 1 ? parts[0] : "Unknown Title";
                // Assign the second part as the artist name or default to "Unknown Artist" if split fails
                String artistName = parts.length > 1 ? parts[1] : "Unknown Artist";
                // Store the full path of the file
                String filePath = file.getAbsolutePath();

                // Select the correct songCreator based on the file type
                SongCreator songCreator;
                if (fileName.endsWith(".aiff"))
                {
                    songCreator = new AIFFSongCreator();
                }
                else if (fileName.endsWith(".wav"))
                {
                    songCreator = new WAVSongCreator();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Unsupported file type.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Use the songCreator to create a new Song object ( Factory Method)
                Song song = songCreator.createSong(songTitle,artistName,filePath);
                // Add the newly created song to the library
                addSongToLibrary(song);
            }
            // Notify all subscribers that the library has been updated
            notifySubscriber();
            // For debugging, print the current list of songs to the console
            System.out.println(getSongs());
        }
    }

    public void addSongToLibrary(Song song)
    {
        this.songs.add(song);
    }

    public void removeSongFromLibraryByFilePath(String filePath)
    {
        // Iterate over the list of songs to find a match by file path
        for(Iterator<Song> iterator = songs.iterator(); iterator.hasNext();)
        {
            Song song = iterator.next();
            //Check if the current song's file path matches the one to remove
            if(song.getFileName().equals(filePath))
            {
                // Remove the matching song from the list
                iterator.remove();
                // Break out of the loop after finding and removing the song
                break;
            }
        }
        // Notify all observers about the change in the song list
        notifySubscriber();
    }

    public void subscribe(LibraryObserver subscriber)
    {
        subscribers.add(subscriber);
        subscriber.update(songs);
    }

    public void unsubscribe(LibraryObserver subscriber)
    {
        subscribers.remove(subscriber);
    }

    public void notifySubscriber()
    {
        for (LibraryObserver subscriber : subscribers)
        {
            subscriber.update(songs);
        }
    }
}
