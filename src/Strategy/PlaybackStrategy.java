package Strategy;

import Model.Song;

import java.util.List;

public interface PlaybackStrategy
{
    void execute(List<Song> playlist, int currentSongIndex);
}
