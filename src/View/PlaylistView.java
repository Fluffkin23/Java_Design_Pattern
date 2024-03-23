package View;

import Model.Playlist;
import Model.Song;
import Observer.PlaylistObserver;

public class PlaylistView implements PlaylistObserver
{
    private Playlist playlist;
    // This variable will be used to check if update has been called in my tests
    private boolean isUpdateCalled = false;
    private PlaylistPlayerUI.UpdateAction updateAction;

    public PlaylistView(Playlist playlist, PlaylistPlayerUI.UpdateAction updateAction)
    {
        this.playlist = playlist;
        this.updateAction = updateAction;
        this.playlist.subscribe(this);
    }

    @Override
    public void update(Playlist playlist) {
        isUpdateCalled = true;
        displayPlaylist(playlist);
        if (updateAction != null) {
            updateAction.execute();
        }
    }

    private void displayPlaylist(Playlist playlist) {
        System.out.println("Updated playlist: " + playlist.getName());
        for (Song song : playlist.getSongs()) {
            System.out.println("Song: " + song.getDetails());
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
        // missing mplementation code to display the playlist in the UI
    }

    public void updatePlaylist()
    {
        showPlaylist(playlist);
    }
}
