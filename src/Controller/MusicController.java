package Controller;

import Factory.MP3Song;
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
    private Playlist currentPlaylist;
    private int currentSong = -1; // Assuming -1 means no song is selected

    private List<MusicControllerObserver> observers = new ArrayList<>();
    private Map<String, Playlist> playlists = new HashMap<>();
    private final File musicDirectory = new File(System.getProperty("user.home"), "Music");



    public MusicController()
    {
        musicLibrary = new MusicLibrary();
        playlists = new HashMap<>();
        loadPlaylistsFromDirectory();

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

    public Playlist getCurrentPlaylist()
    {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(Playlist currentPlaylist)
    {
        this.currentPlaylist = currentPlaylist;
        notifySubscribers();
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
            File[] songFiles = playlistDir.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav"));
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

                if (fileName.endsWith(".mp3")) {
                    song = new MP3Song(title, artist, filePath);
                } else if (fileName.endsWith(".wav")) {
                    song = new WAVSong(title, artist, filePath);
                } else {
                    System.out.println("Unsupported file format for song: " + fileName);
                    return false;
                }

                playlist.addSong(song);
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

        return fileName.endsWith(".mp3") ?
                new MP3Song(title, artist,filePath) :
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

    public Playlist getPlaylist(String name)
    {
        return this.playlists.get(name);
    }

    public int getCurrentSong()
    {
        return currentSong;
    }

    public void setCurrentSong(int currentSong)
    {
        this.currentSong = currentSong;
        notifySubscribers();
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
        }
    }


    public void selectSong(int song)
    {
        currentSong = song;
        notifySubscribers();
    }

    public void playSong()
    {
        if(currentSong >= 0)
        {
            // Logic to play the song
           System.out.println( musicLibrary.getSongByIndex(currentSong).getTitle() );
            musicLibrary.getSongByIndex(currentSong).play();
            notifySubscribers();
            // This would involve more complex logic, potentially involving Java's sound API.
        }

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

