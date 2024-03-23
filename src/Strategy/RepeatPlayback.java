package Strategy;

import Model.Song;

import java.util.List;

public class RepeatPlayback implements PlaybackStrategy
{

    @Override
    public void execute(List<Song> songs)
    {
        if (songs.isEmpty()) return; // if the list is empty

        Song currentSong = songs.get(0); // Assume the first song is the one to repeat

        // We're assuming there's some sort of flag or mechanism to stop repeating.
        // For example, this could be a boolean that gets set to false when the user
        // chooses to stop the playback.

        boolean keepPlaying = true;

        while (keepPlaying)
        {
            currentSong.play();
        }
    }
}
