package View;

import Model.Playlist;
import Model.Song;
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
    public void update()
    {
        System.out.println("Playlist is being updated.");
        updatePlaylist();

    }

    public void showPlaylist(Playlist playlist) {
        // Implementation code to display the playlist in the UI
        System.out.println("Playlist: " + playlist.getName());
        for (Song song : playlist.getSongs()) {
            System.out.println(song.getTitle() + "by" + song.getArtist() );

        }
    }
    public void updatePlaylist()
    {
        showPlaylist(playlist);
    }
}
