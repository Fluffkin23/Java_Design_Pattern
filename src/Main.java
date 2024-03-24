import Controller.MusicController;
import Factory.MP3Song;
import Factory.MP3SongCreator;
import Factory.SongCreator;
import Factory.WAVSongCreator;
import Model.MusicLibrary;
import Model.Song;
import View.LibraryView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {
//        MusicController controller = new MusicController();
//
//
//        // Create song creators for MP3 and WAV formats
//        SongCreator mp3Creator = new MP3SongCreator();
//        SongCreator wavCreator = new WAVSongCreator();
//
//        // Use the creators to make song objects
//        Song mp3Song = mp3Creator.createSong("Title","artist","filepath");
//        Song wavSong = wavCreator.createSong("Title2","artist2","filepath2");
//
//        // Play the songs using the play method
//        System.out.println("Playing MP3 song: " + mp3Song.getDetails());
//        mp3Song.play(controller);
//        // Simulate a pause in the music player
//        mp3Song.pause(controller);
//
//        System.out.println("Playing WAV song: " + wavSong.getDetails());
//        wavSong.play(controller);
//        // Simulate a pause in the music player
//        wavSong.pause(controller);
//------------------------------  LibraryObserver  -----------------------------------------------------
        MusicLibrary library = new MusicLibrary();
        LibraryView libraryView = new LibraryView(library);

        // Subscribe the view to the library updates
        library.subscribe(libraryView);

        // Add some songs to the library
        library.addSong(new MP3Song("Song 1", "Artist 1"));
        library.addSong(new MP3Song("Song 2", "Artist 2"));

        // ... At this point, the libraryView's update method will be called automatically.
        // Here you could handle more logic such as user input, changing songs, etc.
    }
}