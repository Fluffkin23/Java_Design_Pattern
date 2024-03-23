package Factory;

import Model.Song;

public class WAVSong implements Song
{
    private String title;
    private String artist;

    private String filePath;

    public WAVSong(String title, String artist)
    {
        this.title = title;
        this.artist = artist;
    }
    @Override
    public void play()
    {
        // Implementation for playing the song
    }

    @Override
    public void pause()
    {
        // Implementation for pausing the song
    }

    @Override
    public String getDetails()
    {
        return "WAV Song: " + title + " by " + artist;
    }

    @Override
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
}
