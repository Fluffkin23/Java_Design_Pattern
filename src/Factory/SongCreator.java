package Factory;

import Model.Song;

public abstract class SongCreator
{
    public abstract Song createSong(String title, String artist, String filePath);
}
