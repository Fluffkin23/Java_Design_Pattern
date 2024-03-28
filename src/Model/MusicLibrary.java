package Model;

import Controller.MusicController;
import Factory.AIFFSong;
import Factory.WAVSong;
import Observer.LibraryObserver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicLibrary
{
    private List<Song> songs;
    private List<LibraryObserver> subscribers;
    private MusicController musicController;
    private Song currentSong = null;


    public MusicLibrary()
    {
        this.songs = new ArrayList<>();
        this.subscribers = new ArrayList<>();


        loadSongsFromFolder(); // Load initial songs from folder

    }

    public void loadSongsFromFolder()
    {
        // Create a File object pointing to the "Music" directory within the user's home directory.
        // System.getProperty("user.home") dynamically retrieves the path to the current user's home directory,
        // ensuring the code is platform-independent and works across different operating systems.
        File musicFolder = new File(System.getProperty("user.home"), "Music");

        // List all files within the "Music" folder that have either ".aiff" or ".wav" file extensions.
        // The listFiles method is passed a lambda expression (FilenameFilter) that defines the criteria for including files.
        // It checks the file name to see if it ends with ".aiff" or ".wav", ignoring files with other extensions.
        File[] files = musicFolder.listFiles((dir, name) -> name.endsWith(".aiff") || name.endsWith(".wav"));

        // Check if the files array is not null, which can happen if the "Music" directory does not exist or
        // an I/O error occurs. If files is not null, it means there are files (possibly 0) that match the filter criteria.
        if (files != null)
        {
            songs.clear(); // Clear existing songs list
            for (File file : files)
            {
                // Retrieve the name of the file, including its extension (e.g., "Artist - Title.mp3")
                String fileName = file.getName();

                // Remove the file extension from the fileName, leaving only the base name (e.g., "Artist - Title")
                String baseName = fileName.substring(0, fileName.lastIndexOf('.'));

                // Split the base name into two parts using " - " as a delimiter, aiming to separate the artist and title
                String[] parts = baseName.split(" - ", 2);

                // If the split resulted in at least two parts, use the first part as the artist's name;
                // otherwise, default the artist to "Unknown Artist"
                String artist = parts.length > 1 ? parts[0] : "Unknown Artist";

                // If the split resulted in at least two parts, use the second part as the song's title;
                // otherwise, use the whole base name as the title (useful if the file name didn't contain " - ")
                String title = parts.length > 1 ? parts[1] : parts[0];

                // Get the absolute path of the file, which is the full path including directories and file name
                String filePath = file.getAbsolutePath();


                // Check if the file name ends with ".aiff", indicating it's an AIFF audio file. If it does,
                // create a new AIFFSong object using the extracted title, artist, and file path.
                // If the file is not an AIFF file, it assumes the file is a WAV file (based on the context of previous code),
                // and thus creates a new WAVSong object instead, also using the extracted title, artist, and file path.
                // This  operator  chooses between two types of Song objects based on the file extension.
                Song song = fileName.endsWith(".aiff") ? new AIFFSong(title, artist,filePath) : new WAVSong(title, artist,filePath);
                addSong(song);
                //songs.add(song)
            }
            notifySubscriber(); // Notify subscribers about the change

        }

    }

    public void addSong(Song song)
    {
        this.songs.add(song);
        notifySubscriber();
    }

    public List<Song> getSongs()
    {
        return new ArrayList<>(songs); // Returns a copy of the list to prevent external modification.
    }




    public void subscribe(LibraryObserver subscriber)
    {
        subscribers.add(subscriber);
    }

    public void unsubscribe(LibraryObserver subscriber)
    {
        subscribers.remove(subscriber);
    }

    //The notifySubscribers method is marked as private because it's an internal mechanism for the MusicLibrary class
    // to update all its subscribers about changes. It is called whenever a song is added or removed from the library.

    //Call this method whenever the library is updated
    public void notifySubscriber()
    {
        for (LibraryObserver subscriber : subscribers)
        {
            subscriber.update();
        }
    }
}
