package Observer;

import Model.Song;

import java.util.List;

public interface LibraryObserver
{
    void update(List<Song> songs);
}
