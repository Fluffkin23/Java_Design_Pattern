import Factory.MP3Song;
import Factory.WAVSong;
import Model.MusicLibrary;
import View.LibraryView;

import javax.swing.*;

public static void main(String[] args)
{
    SwingUtilities.invokeLater(() -> {
        MusicLibrary library = new MusicLibrary();
        library.addSong(new MP3Song("Song Title 1", "Artist 1"));
        library.addSong(new WAVSong("Song Title 2", "Artist 2"));
        library.addSong(new WAVSong("Song Title 3", "Artist 3"));

        LibraryView view = new LibraryView(library);
        view.showLibrary(); // This method needs to set up and display the JFrame
    });
}
