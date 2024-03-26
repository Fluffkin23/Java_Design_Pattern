package Strategy;

import Model.Song;

import java.util.List;

public class RepeatPlayback implements PlaybackStrategy
{
    private final int songIndex;
    private final int repeatCount;

    public RepeatPlayback(int songIndex, int repeatCount)
    {
        this.songIndex = songIndex;  // Index of the song to repeat
        this.repeatCount = repeatCount;  // Number of times to repeat the song
    }

    @Override
    public void execute(List<Song> playlist)
    {
        if (songIndex >= 0 && songIndex < playlist.size()) {
            Song songToRepeat = playlist.get(songIndex);
            System.out.println("Repeating song: " + songToRepeat.getTitle());
            for (int i = 0; i < repeatCount; i++)
            {
                songToRepeat.play();
            }
            System.out.println("Finished repeating the song.");
        }
        else
        {
            System.out.println("Song index is out of playlist range.");
        }
    }
}
