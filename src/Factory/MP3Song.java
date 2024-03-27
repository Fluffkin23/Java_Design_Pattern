package Factory;

import Controller.MusicController;
import Model.Song;

public class MP3Song implements Song
{
    private String title;
    private String artist;
    private String filePath;

    public MP3Song(String title, String artist, String filePath)
    {
        this.title = title;
        this.artist = artist;
        this.filePath = filePath;
    }

    @Override
    public String getDetails()
    {
        return "MP3 Song: " + title + " by " + artist;
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
        System.out.println("I am playing from Song class");
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
