package Strategy;

import Model.Song;

import java.util.List;

public class NormalPlayback implements PlaybackStrategy
{
    @Override
    public void execute(List<Song> songs)
    {
        Song currentSong;
        int index = 0;
        while (true)
        { // This loop will repeat indefinitely
            if (index >= songs.size()) {
                index = 0; // Reset to the first song after the last song is played
            }
            currentSong = songs.get(index++);
            currentSong.play();
        }
    }
}
