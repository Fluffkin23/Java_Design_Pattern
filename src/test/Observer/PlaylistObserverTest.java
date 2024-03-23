package test.Observer;

import Model.Playlist;
import Observer.PlaylistObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Fake observer for testing purposes
class FakePlaylistObserver implements PlaylistObserver {
    boolean wasUpdated = false;

    @Override
    public void update(Playlist playlist) {
        wasUpdated = true;
    }
}

public class PlaylistObserverTest {
    private Playlist playlist;
    private FakePlaylistObserver observer;

    @BeforeEach
    void setUp() {
        // Initialize a Playlist and the fake observer
        playlist = new Playlist("Test Playlist");
        observer = new FakePlaylistObserver();
        // Register the observer with the Playlist
        playlist.subscribe(observer);
    }

    @Test
    void testObserverIsNotifiedOnUpdate() {
        // Make a change to the playlist that should trigger the observer
        playlist.addSong(null); // Assuming addSong will trigger an update

        // Verify that the observer was notified
        assertTrue(observer.wasUpdated, "Observer should have been notified of the update.");
    }
}
