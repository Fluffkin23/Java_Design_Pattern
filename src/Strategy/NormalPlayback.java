package Strategy;

import Model.Song;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.util.List;

public class NormalPlayback implements PlaybackStrategy
{
    private Clip clip;
    private List<Song> playlist;
    private boolean isPaused = true;
    private int currentSongIndex = 0;

    @Override
    public void play(List<Song> playlist)
    {
        this.playlist = playlist;
        playSongAtIndex(currentSongIndex);
    }

    private void playSongAtIndex(int index)
    {
        // Check if the playlist is empty or null before proceeding
        if (playlist == null || playlist.isEmpty())
        {
            System.out.println("Playlist is empty.");
            return;
        }
        // Ensure the index is within the bounds of the playlist
        if (index < 0 || index >= playlist.size())
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
            File audioFile = new File(playlist.get(currentSongIndex).getFilePath());
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));
            clip.start();
            isPaused = false; // Ensure playback isn't paused

            // Add a listener to handle the end of the song
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && !isPaused) {
                    // Increment the currentSongIndex to move to the next song
                    int nextIndex = (currentSongIndex + 1) % playlist.size();

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
//        if (playlist == null || playlist.isEmpty())
//        {
//        System.out.println("Playlist is empty.");
//        return;
//        }
//
//        isPaused = false; // Ensure playback isn't paused
//
//        currentSongIndex++; // Increment the song index
//        if (currentSongIndex >= playlist.size())
//        {
//            currentSongIndex = 0; // Wrap to start of the playlist if at the end
//        }
//
//
//        System.out.println("Playing next song at index: " + currentSongIndex);
//        playSongAtIndex(currentSongIndex);
    }


    @Override
    public void previous()
    {
//        if (--currentSongIndex < 0) {
//            currentSongIndex = playlist.size() - 1; // Wrap to end of the playlist
//        }
//
//        playSongAtIndex(currentSongIndex);
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
        if (playlist != null && !playlist.isEmpty() && getCurrentSongIndex() >= 0 && getCurrentSongIndex() < playlist.size())
        {
            return playlist.get(getCurrentSongIndex());
        }
        return null;
    }

    public void setVolume(float volume)
    {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume); // Reduce volume by a number of decibels.
        }
    }
}