package Factory;

import Model.Song;

public class MP3SongCreator extends SongCreator
{

    @Override
    public Song createSong(String title, String artist,String filePath)
    {
        MP3Song song = new MP3Song(title, artist,filePath);
        return song;
    }
}
