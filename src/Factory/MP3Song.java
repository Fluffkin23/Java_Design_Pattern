package Factory;

import Model.Song;

public class MP3Song implements Song {
    private String title;
    private String artist;
    private String filePath;

    public MP3Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public void play() {
        // Implementation for playing the song
        System.out.println("Playing MP3 song: " + title + " by " + artist);
    }

    @Override
    public void pause() {
        // Implementation for pausing the song
        System.out.println("Pausing MP3 song: " + title + " by " + artist);
    }

    @Override
    public String getDetails() {
        return "MP3 Song: " + title + " by " + artist;
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // Getter methods if needed
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getFilePath() {
        return filePath;
    }
}
