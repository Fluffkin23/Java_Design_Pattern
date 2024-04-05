package Strategy;

import Model.Song;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.util.List;

public class RepeatPlayback implements PlaybackStrategy
{
    private Clip clip;
    private List<Song> playlist;
    private boolean isRepeat = true;
    private boolean isPaused = true;
    private int currentSongIndex = 0;

    @Override
    public void play(List<Song> playlist)
    {
        this.playlist = playlist;
        if (!playlist.isEmpty() && currentSongIndex >= 0 && currentSongIndex < playlist.size())
        {
            playSongAtIndex(currentSongIndex);
        }
    }

    private void playSongAtIndex(int index)
    {
        try
        {
            if (clip != null)
            {
                clip.close();
            }
            File audioFile = new File(playlist.get(index).getFilePath());
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the current song continuously
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && !isRepeat)
                {
                    clip.setFramePosition(0); // Reset to start of the song
                    clip.start(); // Restart the song
                }
            });
        }
        catch (Exception e)
        {
            System.err.println("Playback failed for the song.");
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
        if (playlist == null || playlist.isEmpty())
        {
            System.out.println("Playlist is empty.");
            return;
        }

        isPaused = false; // Ensure playback isn't paused

        currentSongIndex++; // Increment the song index
        if (currentSongIndex >= playlist.size())
        {
            currentSongIndex = 0; // Wrap to start of the playlist if at the end
        }
        System.out.println("Playing next song at index: " + currentSongIndex);
        playSongAtIndex(currentSongIndex);
    }

    @Override
    public void previous()
    {
        if (--currentSongIndex < 0)
        {
            currentSongIndex = playlist.size() - 1; // Wrap to end of the playlist
        }
        playSongAtIndex(currentSongIndex);
    }

    @Override
    public void setCurrentSongIndex(int index)
    {
        this.currentSongIndex = index;
    }

    @Override
    public int getCurrentSongIndex() {
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
