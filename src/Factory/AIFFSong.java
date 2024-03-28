package Factory;

import Controller.MusicController;
import Model.Song;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AIFFSong implements Song
{
    private String title;
    private String artist;
    private String filePath;
    private Clip clip = null;
    private boolean isPaused = false;

    public AIFFSong(String title, String artist, String filePath)
    {
        this.title = title;
        this.artist = artist;
        this.filePath = filePath;
    }

    @Override
    public String getDetails()
    {
        return "AIFF Song: " + title + " by " + artist;
    }

    @Override
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    @Override
    public String getFilePath()
    {
        return this.filePath;
    }


    @Override
    public void play()
    {
        if (clip == null) { // First time play is called
            try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath))) {
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (isPaused) { // If the song was paused
            clip.start(); // Resume playing
            isPaused = false;
        } // No need for an else block if the song is already playing
    }

    @Override
    public void pause()
    {
        if (clip != null && clip.isRunning()) {
            clip.stop(); // Pause playback
            isPaused = true;
        }
    }

    public void stop()
    {
        if (clip != null)
        {
            clip.stop();
            clip.close();
            clip = null; // Ready the song to be played from the beginning next time
            isPaused = false;
        }
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getArtist()
    {
        return this.artist;
    }

}
