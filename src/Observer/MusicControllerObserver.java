package Observer;

import Model.Song;

public interface MusicControllerObserver
{
    void update();
    void onSongChange(Song currentSong);
}
