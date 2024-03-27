package Factory;

import Model.Song;

public class AIFFSongCreator extends SongCreator
{

    @Override
    public Song createSong(String title, String artist,String filePath)
    {
        AIFFSong song = new AIFFSong(title, artist,filePath);
        return song;
    }
}
