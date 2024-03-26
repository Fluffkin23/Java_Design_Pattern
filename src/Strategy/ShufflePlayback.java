package Strategy;

import Model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShufflePlayback implements PlaybackStrategy
{
    private Random random = new Random();



    @Override
    public void execute(List<Song> playlist)
    {
        if (playlist.isEmpty())
        {
            System.out.println("The playlist is empty.");
            return;
        }

        List<Integer> playedIndices = new ArrayList<>();
        for (int i = 0; i < playlist.size(); i++)
        {
            playedIndices.add(i); // Initialize an index list
        }
        Collections.shuffle(playedIndices); // Shuffle the indices

        System.out.println("Starting shuffle playback.");

        for (Integer index : playedIndices)
        {
            playlist.get(index).play();
            System.out.println (playlist.get(index).getTitle());

        }

        System.out.println("Shuffle playback complete.");

    }
}
