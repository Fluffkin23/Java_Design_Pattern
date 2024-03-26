package View;

import Model.MusicLibrary;
import Model.Song;
import Observer.LibraryObserver;

public class LibraryView implements LibraryObserver
{
    private MusicLibrary library;

    public LibraryView(MusicLibrary library)
    {
        this.library = library;

    }

    //This library is called when the library is updated
    @Override
    public void update()
    {
        System.out.println("LibraryView is being updated.");
        updateLibrary();
    }

    // Call this method to show the library in the UI
    public void showLibrary(MusicLibrary library)
    {
        System.out.println("Displaying updated library:");
        // Display the library in some way
        for (Song song : library.getSongs()) {
            System.out.println(song.getDetails());
        }
    }

    // Call this method to update the library in the GUI
    public void updateLibrary()
    {
        // This method might be responsible for fetching new data from the library
        // and updating the view accordingly.
        showLibrary(library); //
    }
}
