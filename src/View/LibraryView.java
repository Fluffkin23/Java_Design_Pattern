package View;

import Model.MusicLibrary;
import Observer.LibraryObserver;

public class LibraryView implements LibraryObserver
{
    private MusicLibrary library;

    public LibraryView(MusicLibrary library)
    {
        this.library = library;
        this.library.subscribe(this); // Subscribe to library updates
    }

    //This library is called when the library is updated
    @Override
    public void update()
    {

    }

    // Call this method to show the library in the UI
    public void showLibrary(MusicLibrary library)
    {
        //Implementation code to display the library
    }

    // Call this method to update the library in the GUI
    public void updateLibrary()
    {
        showLibrary(library); //
    }
}
