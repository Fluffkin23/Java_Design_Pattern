package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlaylistLibrary
{
    private List<Playlist> playlistList;
    public PlaylistLibrary()
    {
        this.playlistList = new ArrayList<>();
    }
    public List<Playlist> getPlaylistList()
    {
        for(Playlist playlist: playlistList)
        {
            System.out.println(playlist.getPlaylistName());
        } // it does not return the playlists
        return playlistList;
    }

    public void setPlaylistList(List<Playlist> playlistList)
    {
        this.playlistList = playlistList;
    }

    public Playlist getPlaylistByName(String playlistName)
    {
        for(Playlist playlist : playlistList)
        {
            if(playlist.getPlaylistName().equals(playlistName))
                return playlist;
        }
        return null;
    }

    public void addPlaylistToLibrary(Playlist playlist)
    {
        this.playlistList.add(playlist);
    }

    // Define a static method to load playlist information from the Music Folder
    public  List<Playlist> loadPlaylistsFromMusicFolder()
    {
        // Construct the path to the Music directory in the user's home folder.
        String musicDirectoryPath = System.getProperty("user.home") + File.separator + "Music";
        // Create a File object representing the Music directory.
        File musicDirectory = new File(musicDirectoryPath);
        // List all directories within the Music directory, as each one represents a playlist.
        File[] directories = musicDirectory.listFiles(File::isDirectory);
        //Check if any directories were found
        if(directories != null)
        {
            //Iterate over each directory found in the Music folder
            for (File dir : directories)
            {
                // Create a new Playlist object using the name of the directory (playlist name)
                Playlist playlist = new Playlist(dir.getName());
                // Add the newly created Playlist object to the list of loaded playlists.
                this.playlistList.add(playlist);
            }
        }
        // Return the list of Playlist objects loaded from the Music folder.
        for(Playlist playlist : this.playlistList)
        {
            System.out.println(playlist.getPlaylistName());
        }
        return this.playlistList;
    }

    public boolean removePlaylist(String playlistName)
    {
        // First, try to find the playlist with the given name
        Playlist playlistToRemove = null;
        for (Playlist playlist : this.playlistList)
        {
            if (playlist.getPlaylistName().equals(playlistName))
            {
                playlistToRemove = playlist;
                break;
            }
        }
        // If a matching playlist is found, attempt to delete its directory
        if(playlistToRemove !=null )
        {
            File playlistDirectory = new File(System.getProperty("user.home") + File.separator + "Music" +
                    File.separator + playlistName);

            System.out.println("Attempting to delete: " + playlistDirectory.getAbsolutePath()); // testing purpose
            if (deleteDirectory(playlistDirectory))
            {
                // If the directory was successfully deleted, remove the playlist from the list
                this.playlistList.remove(playlistToRemove);
                return true; // Return true if the playlist was successfully deleted
            }
        }
        return false;
    }

    // Method to recursively delete a directory and all of its contents.
    public boolean deleteDirectory(File diretoryToBeDeleted)
    {
        // Retrieve a list of all files and directories within the directory to be deleted.
        File[] allContents = diretoryToBeDeleted.listFiles();
        // Check if the directory contains files or subdirectories.
        if (allContents != null)
        {
            // Iterate over each entry within the directory.
            for(File file : allContents)
            {
                // Recursively call deleteDirectory on each entry.
                // This ensures that subdirectories and their contents are deleted before attempting to delete the directory itself.
                deleteDirectory(file);
            }
        }
        // Attempt to delete the directory itself after its contents have been cleared.
        // The delete method returns true if the directory was successfully deleted.
        return diretoryToBeDeleted.delete();
    }
}