package Strategy;

import Model.Song;

import java.util.Collections;
import java.util.List;

public class ShufflePlayback implements PlaybackStrategy {
    @Override
    public void execute(List<Song> songs)
    {
        Collections.shuffle(songs);
        for (Song song : songs)
        {
            song.play();
        }
    }
}
