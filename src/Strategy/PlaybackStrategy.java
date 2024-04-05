package Strategy;

import Model.Song;

import java.util.List;

public interface PlaybackStrategy
{
    void play(List<Song> playlist);
    void pause();
    void resume();
    void next();
    void previous();
    void setCurrentSongIndex(int index);
    int getCurrentSongIndex();
    Song getCurrentSong();
    void setVolume(float volume);
}
