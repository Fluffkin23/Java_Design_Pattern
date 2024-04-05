package Factory;

import Model.Song;

public class AIFFSong implements Song
{
    private String title;
    private String artist;
    private String filePath;

    public AIFFSong(String title, String artist,String filePath)
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
        return this.filePath;
    }
}
