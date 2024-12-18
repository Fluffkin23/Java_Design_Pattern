package Factory;

import Model.Song;

import java.io.File;

public class WAVSong implements Song
{
    private String title;
    private String artist;
    private String filePath;

    public WAVSong(String title, String artist,String filePath)
    {
        this.title = title;
        this.artist = artist;
        this.filePath = filePath;
    }

    @Override
    public String getTitle()
    {
        return this.title;
    }

    @Override
    public String getArtist()
    {
        return this.artist;
    }

    @Override
    public String getFilePath()
    {
        return this.filePath;
    }

    @Override
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    @Override
    public String getFileName()
    {
        return new File(filePath).getName();
    }
}
