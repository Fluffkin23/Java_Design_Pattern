package Controller;

import Factory.AIFFSong;
import Factory.WAVSong;
import Model.MusicLibrary;
import Model.Playlist;
import Model.Song;
import Observer.MusicControllerObserver;
import Strategy.PlaybackStrategy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class MusicController
{

    private MusicLibrary musicLibrary;
    private Song currentSong1; // This holds the currently playing song

    private Playlist currentPlaylist;
    private Song currentSong = null;

    private List<MusicControllerObserver> observers = new ArrayList<>();
    private Map<String, Playlist> playlists = new HashMap<>();
    private final File musicDirectory = new File(System.getProperty("user.home"), "Music");
    private String currentPlaylistName = null; // Initialized to null or a default playlist name




    public MusicController(MusicLibrary library)
    {
        playlists = new HashMap<>();
        loadPlaylistsFromDirectory();
        this.musicLibrary = library;

    }
//------------------------------------------MusicLibrary----------------------------------------------------------------

    public void playSongByTitle(String title)
    {
        // Utilizes the Java Stream API to search through a collection of Song objects (returned by getSongs()).
        // The filter operation is applied to stream elements to select songs whose title matches the specified 'title'.
        Song songToPlay = getMusicLibrary().getSongs().stream()
                .filter(song -> song.getTitle().equals(title))
                .findFirst() // Attempts to find the first song that matches the filter condition.
                .orElse(null);// If no song matches the filter condition, 'orElse(null)' ensures that songToPlay
        // is set to null instead of throwing an error.

        // Checks if a song matching the title was found.
        if (songToPlay != null)
        {
            // If a song was found, its play() method is called to start playback.
            songToPlay.play();
            currentSong = songToPlay;
        }
        else
        {
            // If no song matching the title was found, a message is printed to the console indicating that the song was not found.
            System.out.println("Song not found: " + title);
        }
    }

    public void pauseCurrentSong()
    {
        if (currentSong != null)
        {
            currentSong.pause(); // Pause the song
        }
    }

    public void stopCurrentSong()
    {
        if (currentSong != null)
        {
            currentSong.stop(); // Stop the song
            currentSong = null; // Clear the currently playing song
        }
    }

    public void playPlaylist(Playlist playlist) {
        // Iterate through playlist and play each song
        for (Song song : playlist.getSongs()) {
                song.play();
            // Add a delay or a mechanism to wait for the song to finish before playing the next one
        }
    }

    // Getters and setters
    public MusicLibrary getMusicLibrary()
    {
        return musicLibrary;
    }

    public void setMusicLibrary(MusicLibrary musicLibrary)
    {
        this.musicLibrary = musicLibrary;
        notifySubscribers();
    }

    public void addSongLibrary(Song song)
    {
        this.musicLibrary.addSong(song);
    }


    public void setCurrentPlaylist(Playlist currentPlaylist)
    {
        this.currentPlaylist = currentPlaylist;
        notifySubscribers();
    }

    public void loadAndPlayPlaylist(File playlistFolder)
    {
        Playlist newPlaylist = new Playlist(playlistFolder.getName());
        File[] songFiles = playlistFolder.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav"));

        if (songFiles != null)
        {
            for (File songFile : songFiles)
            {
                // Assuming you have a method to create a Song object from a file
                Song song = createSongFromFile(songFile);
                newPlaylist.addSong(song);
            }
            setCurrentPlaylist(newPlaylist); // Set the new playlist as current
            playPlaylist(newPlaylist); // Play the newly loaded playlist
        }
    }


    private void loadPlaylistsFromDirectory()
    {
        if(!musicDirectory.exists() || !musicDirectory.isDirectory())
        {
            return; // Music directory does not exist or is not a directory
        }
        // used to list all directories within a specified directory --> musicDirectory
        File[] playlistDirectories = musicDirectory.listFiles(File :: isDirectory);
        if(playlistDirectories == null ) return;

        for( File playlistDir : playlistDirectories)
        {
            Playlist playlist = new Playlist(playlistDir.getName());
            File[] songFiles = playlistDir.listFiles((dir, name) -> name.endsWith(".aiff") || name.endsWith(".wav"));
            if(songFiles != null )
            {
                for(File songFile : songFiles)
                {
                    Song song = createSongFromFile(songFile);
                    if(song != null )
                    {
                        playlist.addSong(song);
                    }
                }
            }
            playlists.put(playlistDir.getName(), playlist);
        }
    }

    public Set<String> getPlaylistName()
    {
        return playlists.keySet(); // This return a Set view of the keys in the playlist map
    }

    public boolean addSongToPlaylist(Song song, String playlistName) {
        Playlist playlist = playlists.get(playlistName);
        if (playlist != null)
        {
            try
            {

                File sourceFile = new File(song.getFilePath());

                String fileName = sourceFile.getName();
                String title = extractTitleFromFileName(fileName);
                String artist = extractArtistFromFileName(fileName);
                String filePath = sourceFile.getAbsolutePath();

                File playlistDirectory = new File(musicDirectory, playlistName);
                if (!playlistDirectory.exists())
                {
                    playlistDirectory.mkdirs(); // Create the playlist directory if it doesn't exist
                }

                File destinationFile = new File(playlistDirectory, sourceFile.getName());
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Update your playlist object as necessary, possibly with the new file path

                if (fileName.endsWith(".aiff")) {
                    song = new AIFFSong(title, artist, filePath);
                } else if (fileName.endsWith(".wav")) {
                    song = new WAVSong(title, artist, filePath);
                } else {
                    System.out.println("Unsupported file format for song: " + fileName);
                    return false;
                }

                playlist.addSong(song);
                notifySubscribers();
                return true;

            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false; // Copy failed
            }
        }
        return false; // Playlist not found
    }

    private String extractTitleFromFileName(String fileName) {
        // Implementation to extract title from the file name, assuming format "Artist - Title.mp3/wav"
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        String[] parts = fileNameWithoutExtension.split(" - ", 2);
        return parts.length > 1 ? parts[1] : fileNameWithoutExtension; // Default to whole name if pattern not matched
    }

    private String extractArtistFromFileName(String fileName) {
        // Implementation to extract artist from the file name, assuming format "Artist - Title.mp3/wav"
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        String[] parts = fileNameWithoutExtension.split(" - ", 2);
        return parts.length > 1 ? parts[0] : "Unknown Artist"; // Default to "Unknown Artist" if pattern not matched
    }

    public Song createSongFromFile(File songFile)
    {
        String fileName = songFile.getName();
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        String[] parts = fileNameWithoutExtension.split(" - ", 2);
        String artist = parts.length > 1 ? parts[0] : "Unknown Artist";
        String title = parts.length > 1 ? parts[1] : parts[0];
        String filePath = songFile.getAbsolutePath();

        return fileName.endsWith(".aiff") ?
                new AIFFSong(title, artist,filePath) :
                new WAVSong(title, artist,filePath);
    }

    public Playlist createPlaylist(String name)
    {
        if (playlists.containsKey(name))
        {
            System.out.println("Playlist already exists.");
            return null;
        }
        File playlistDirectory = new File(musicDirectory, name);
        if (!playlistDirectory.exists())
        {
            playlistDirectory.mkdirs();
        }
        Playlist newPlaylist = new Playlist(name);
        playlists.put(name, newPlaylist);
        return newPlaylist;
    }

    private void loadPlaylistFromFolder(File folder)
    {
        Playlist newPlaylist = new Playlist(folder.getName());
        for(File file : folder.listFiles())
        {
            if (file.isFile() && (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav")))
            {

            }
        }
    }

    public Playlist getPlaylist(String name)
    {
        return this.playlists.get(name);
    }

    public Playlist getCurrentPlaylist()
    {
        if (currentPlaylistName != null) {
            return getPlaylist(currentPlaylistName);
        }
        return null;
    }


    public void setCurrentPlaylistName(String name) {
        this.currentPlaylistName = name;
        // Additional logic might be added here, such as loading the playlist's songs
    }

    // Observer-related methods
    public void subscribe(MusicControllerObserver observer)
    {
        observers.add(observer);
    }

    public void unsubscribe(MusicControllerObserver observer)
    {
        observers.remove(observer);
    }

    private void notifySubscribers()
    {
        for (MusicControllerObserver observer : observers)
        {
            observer.update();
            observer.onSongChange(currentSong);

        }
    }


    public void selectSong(Song song)
    {

        notifySubscribers();
    }

    public void playSong()
    {
            notifySubscribers();
            // This would involve more complex logic, potentially involving Java's sound API
    }

    // Method to pause a song
    public void pauseSong(Song song)
    {
        // Logic to pause the song
        System.out.println("Pausing: " + song.getDetails());
        // Similarly, actual pause logic would be more involved.
        notifySubscribers();
    }

    public void changeVolume(int level)
    {
        notifySubscribers();
    }

    // ... rest of the MusicController methods
}

