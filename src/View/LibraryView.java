package View;

import Model.MusicLibrary;
import Observer.LibraryObserver;

public class LibraryView implements LibraryObserver
{
    private MusicLibrary library;

    public LibraryView(MusicLibrary library)
    {
        this.library = library;
    }

    @Override
    public void update() {
        showLibrary(library);
    }

    public void showLibrary(MusicLibrary library) {
        // Implementation code to display the library
    }

    public void updateLibrary() {
        // Implementation code to update the view when the library changes
    }
}
