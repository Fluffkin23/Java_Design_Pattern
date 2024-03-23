package View;

import Model.Playlist;
import Observer.PlaylistObserver;

public class PlaylistView implements PlaylistObserver
{
    private Playlist playlist;

    public PlaylistView(Playlist playlist)
    {
        this.playlist = playlist;
        this.playlist.subscribe(this); // Subscribe to playlist updates
    }

    @Override
    public void update() {
        updatePlaylist();
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
