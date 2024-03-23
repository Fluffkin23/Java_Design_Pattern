package test.Model;

import Model.Playlist;
import Model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {

    private Playlist playlist;
    private Song song1;
    private Song song2;

    // Simple concrete implementation of Song for testing
    class TestSong implements Song {
        private String title;
        private String filePath;

        public TestSong(String title) {
            this.title = title;
        }

        @Override
        public void play() {
            // For testing purposes, no implementation is needed.
        }

        @Override
        public void pause() {
            // For testing purposes, no implementation is needed.
        }

        @Override
        public String getDetails() {
            return "TestSong: " + title;
        }

        @Override
        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public String getArtist() {
            return null;
        }

        // Additional methods or overrides for testing as needed
    }

    @BeforeEach
    void setUp() {
        playlist = new Playlist("Test Playlist");
        song1 = new TestSong("Song 1");
        song2 = new TestSong("Song 2");
    }

    @Test
    void testAddSong() {
        assertTrue(playlist.getSongs().isEmpty(), "Playlist should be empty initially");

        playlist.addSong(song1);
        assertEquals(1, playlist.getSongs().size(), "Playlist should contain one song after adding a song");
        assertTrue(playlist.getSongs().contains(song1), "Playlist does not contain the added song");

        playlist.addSong(song2);
        assertEquals(2, playlist.getSongs().size(), "Playlist should contain two songs after adding another song");
        assertTrue(playlist.getSongs().contains(song2), "Playlist does not contain the second added song");
    }

    @Test
    void testRemoveSong() {
        playlist.addSong(song1);
        playlist.addSong(song2);

        playlist.removeSong(song1);
        assertEquals(1, playlist.getSongs().size(), "Playlist should contain one song after removing a song");
        assertFalse(playlist.getSongs().contains(song1), "Playlist still contains the removed song");

        playlist.removeSong(song2);
        assertTrue(playlist.getSongs().isEmpty(), "Playlist should be empty after removing all songs");
    }
}
