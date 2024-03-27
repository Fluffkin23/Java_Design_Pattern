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
        // Implementation for playing AIFF files, similar to WAV
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath))) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(10);
            clip.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause(MusicController controller)
    {

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
