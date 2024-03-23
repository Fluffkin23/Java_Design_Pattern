package View;

import Model.MusicLibrary;
import Observer.LibraryObserver;

public class LibraryView implements LibraryObserver
{
    private MusicLibrary library;

    public LibraryView(MusicLibrary library)
    {
        this.library = library;
        this.library.subscribe(this);
    }

    @Override
    public void update() {
        // Refresh the UI component that displays the library's songs
        updateLibrary();
    }

    public void showLibrary(MusicLibrary library) {
        // missing UI code to display the library's content
    }

    public void updateLibrary() {
        showLibrary(library);
    }
}
