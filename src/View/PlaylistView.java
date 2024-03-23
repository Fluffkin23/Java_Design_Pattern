package View;

import Model.Playlist;
import Model.Song;
import Observer.PlaylistObserver;

public class PlaylistView implements PlaylistObserver
{
    private Playlist playlist;
    // This variable will be used to check if update has been called in my tests
    private boolean isUpdateCalled = false;

    public PlaylistView(Playlist playlist)
    {
        this.playlist = playlist;
        this.playlist.subscribe(this); // Subscribe to playlist updates
    }

    // Method from the PlaylistObserver interface
    @Override
    public void update(Playlist playlist) {
        // Set the flag to true to indicate that an update has occurred
        isUpdateCalled = true;

        // Call the method to process the playlist update
        displayPlaylist(playlist);
    }

    // This method will process the playlist update
    private void displayPlaylist(Playlist playlist) {
        // Process the playlist
        // For now, this can be empty or simply log to the console
        // In the future, you can add logic here to update the UI or otherwise react to the update

        // For example, you could log the playlist's contents:
        System.out.println("Updated playlist: " + playlist.getName());
        for (Song song : playlist.getSongs()) {
            // Log each song's details
            System.out.println("Song: " + song.toString()); // Assuming Song class has a proper toString() implementation
        }
    }

    // Method to check if update has been called, useful for testing
    public boolean isUpdateCalled() {
        return isUpdateCalled;
    }

    // Method to reset the update called flag, useful for testing
    public void resetUpdateCalled() {
        isUpdateCalled = false;
    }

    public void showPlaylist(Playlist playlist)
    {
        // Implementation code to display the playlist in the UI
    }

    public void updatePlaylist()
    {
        showPlaylist(playlist);
    }
}
