package Factory;

import Model.Song;

public class WAVSongCreator extends SongCreator
{
    @Override
    public Song createSong(String title, String artist,String filePath)
    {
        WAVSong song = new WAVSong(title,artist,filePath);
        return song;
    }
}
