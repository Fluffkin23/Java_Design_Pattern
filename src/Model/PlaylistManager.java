package Model;

import Factory.MP3Song;
import Factory.WAVSong;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlaylistManager
{
    private Map<String, Playlist> playlists = new HashMap<>();
    private final File musicDirectory = new File(System.getProperty("user.home"), "Music");

    public PlaylistManager() {
        playlists = new HashMap<>();
        //musicDirectory = new File(System.getProperty("user.home"), "Music");
        loadPlaylistsFromDirectory();
    }

    private void loadPlaylistsFromDirectory() {
        if (!musicDirectory.exists() || !musicDirectory.isDirectory()) {
            return; // Music directory does not exist or is not a directory
        }

        File[] playlistDirectories = musicDirectory.listFiles(File::isDirectory);
        if (playlistDirectories == null) return;

        for (File playlistDir : playlistDirectories) {
            Playlist playlist = new Playlist(playlistDir.getName());
            File[] songFiles = playlistDir.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav"));
            if (songFiles != null) {
                for (File songFile : songFiles) {
                    // Assuming you have a method to create a Song object from a file
                    Song song = createSongFromFile(songFile);
                    if (song != null) {
                        playlist.addSong(song);
                    }
                }
            }
            playlists.put(playlistDir.getName(), playlist);
        }
    }
    public Set<String> getPlaylistNames()
    {
        return playlists.keySet(); // This returns a Set view of the keys in the playlists map
    }

    private Song createSongFromFile(File songFile)
    {
        String fileName = songFile.getName();
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        String[] parts = fileNameWithoutExtension.split(" - ", 2);
        String artist = parts.length > 1 ? parts[0] : "Unknown Artist";
        String title = parts.length > 1 ? parts[1] : parts[0];

        return fileName.endsWith(".mp3") ? new MP3Song(title, artist) : new WAVSong(title, artist);
    }

    public Playlist createPlaylist(String name) {
        if (playlists.containsKey(name)) {
            System.out.println("Playlist already exists.");
            return null;
        }
        File playlistDirectory = new File(musicDirectory, name);
        if (!playlistDirectory.exists()) {
            playlistDirectory.mkdirs();
        }
        Playlist newPlaylist = new Playlist(name);
        playlists.put(name, newPlaylist);
        return newPlaylist;
    }

    public void deletePlaylist(String name) {
        // Additional logic to delete playlist folder and remove from map
    }

    public Playlist getPlaylist(String name) {
        return playlists.get(name);
    }
}
