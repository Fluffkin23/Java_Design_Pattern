package Strategy;

import Model.Song;

import java.util.List;

public class NormalPlayback implements PlaybackStrategy
{
    @Override
    public void execute(List<Song> playlist)
    {
        System.out.println("Starting normal playback.");
        for (Song song : playlist) {
                song.play();
        }
        System.out.println("Reached end of playlist.");
    }
}
