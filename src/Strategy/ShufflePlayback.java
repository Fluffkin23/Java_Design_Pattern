package Strategy;

import Model.Song;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShufflePlayback implements PlaybackStrategy
{
    private List<Song> shuffledPlaylist = new ArrayList<>();
    private int currentSongIndex = -1;
    private Clip clip;
    private boolean isPaused = false;

    @Override
    public void play(List<Song> playlist)
    {
        if (shuffledPlaylist.isEmpty() || playlist.size() != shuffledPlaylist.size())
        {
            // Create a new shuffled playlist
            shuffledPlaylist = new ArrayList<>(playlist);
            Collections.shuffle(shuffledPlaylist, new Random());
            currentSongIndex = 0; // Start from the first song in the shuffled list
        }
        playSongAtIndex(currentSongIndex);
    }

    private void playSongAtIndex(int index)
    {
        // Check if the playlist is empty or null before proceeding
        if (shuffledPlaylist == null || shuffledPlaylist.isEmpty())
        {
            System.out.println("Playlist is empty.");
            return;
        }
        // Ensure the index is within the bounds of the playlist
        if (index < 0 || index >= shuffledPlaylist.size())
        {
            System.out.println("Index out of bounds for playlist.");
            return; // Exit the method if the index is out of bounds
        }
        currentSongIndex = index; // Update the current song index
        try
        {
            // Stop and close the previous clip if it exists
            if (clip != null)
            {
                clip.stop();
                clip.close();
            }

            // Load and play the new audio file
            File audioFile = new File(shuffledPlaylist.get(currentSongIndex).getFilePath());
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));
            clip.start();
            isPaused = false; // Ensure playback isn't paused

            // Add a listener to handle the end of the song
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && !isPaused)
                {
                    // Increment the currentSongIndex to move to the next song
                    int nextIndex = (currentSongIndex + 1) % shuffledPlaylist.size();

                    // Directly call playSongAtIndex for the next song, effectively looping the playlist
                    playSongAtIndex(nextIndex);
                }
            });
        }
        catch (Exception e)
        {
            System.err.println("Playback failed for song at index " + currentSongIndex + ".");
            e.printStackTrace();
        }
    }

    @Override
    public void pause()
    {
        if (clip != null && clip.isRunning())
        {
            isPaused = true;
            clip.stop();
        }
        else if (clip != null && !clip.isRunning() && isPaused)
        {
            resume();
        }
        System.out.println("Paused");
    }

    @Override
    public void resume()
    {
        if (clip != null && !clip.isRunning())
        {
            clip.start();
            isPaused = false;
        }
    }

    @Override
    public void next()
    {
//        if (currentSongIndex >= 0 && currentSongIndex < shuffledPlaylist.size() - 1) {
//            // There is a next song in the list
//            currentSongIndex++;
//        } else {
//            // End of the list reached, loop back to the start or stop playback
//            currentSongIndex = 0; // Loop back to start
//            // For stopping playback instead of looping, you could just return or set a flag to indicate playback should stop.
//        }
//
//        playSongAtIndex(currentSongIndex);
    }

    @Override
    public void previous()
    {

    }

    @Override
    public void setCurrentSongIndex(int index)
    {
        this.currentSongIndex = index;
    }

    @Override
    public int getCurrentSongIndex()
    {
        return this.currentSongIndex;
    }

    @Override
    public Song getCurrentSong()
    {
        if (this.shuffledPlaylist != null && !this.shuffledPlaylist.isEmpty() && getCurrentSongIndex() >= 0 && getCurrentSongIndex() < this.shuffledPlaylist.size())
        {
            return this.shuffledPlaylist.get(getCurrentSongIndex());
        }
        return null;
    }

    @Override
    public void setVolume(float volume)
    {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN))
        {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume); // Reduce volume by a number of decibels.
        }
    }
}
