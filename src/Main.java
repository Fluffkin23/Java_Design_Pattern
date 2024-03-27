import Controller.MusicController;
import Factory.*;
import Model.MusicLibrary;
import Model.Playlist;
import Model.Song;
import Strategy.PlaybackStrategy;
import Strategy.ShufflePlayback;
import View.LibraryView;
import View.MusicPlayerUI;
import View.PlaylistView;

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
//
//------------------------------  LibraryObserver  ---------------------------------------------------------------------
//        MusicLibrary library = new MusicLibrary();
//        LibraryView libraryView = new LibraryView(library);
//
//        // Subscribe the view to the library updates
//        library.subscribe(libraryView);
//
//        // Add some songs to the library
//        library.addSong(new MP3Song("Song 1", "Artist 1"));
//       library.addSong(new MP3Song("Song 2", "Artist 2"));
//
//        // ... At this point, the libraryView's update method will be called automatically.
//        // Here you could handle more logic such as user input, changing songs, etc.
//
//
// ----------------------------------------------- PlayList ------------------------------------------------------------
//
//
//        // Create a new playlist
//        Playlist myPlaylist = new Playlist("My Favorite Songs");
//
//        // Create a view for the playlist which automatically registers itself as an observer
//        PlaylistView myPlaylistView = new PlaylistView(myPlaylist);
////        myPlaylist.addSong(mp3Song);
////        myPlaylist.addSong(wavSong);
//
//        // Add some songs to the playlist
//        myPlaylist.addSong(new MP3Song("Song 1", "Artist A"));
//        myPlaylist.addSong(new MP3Song("Song 2", "Artist B"));
//        myPlaylist.addSong(new WAVSong("Song 3", "Artist C"));
//
//        // The view should update automatically every time the playlist is modified
//        System.out.println("After adding songs:");
//        myPlaylistView.showPlaylist(myPlaylist);
//--------------------------------------------------Music Controller ---------------------------------------------------
//
//        MusicLibrary library = new MusicLibrary();
//        LibraryView libraryView = new LibraryView(library);
//
//        // Subscribe the view to the library updates
//        library.subscribe(libraryView);
//
//        // Add some songs to the library
//        library.addSong(new MP3Song("Song 1", "Artist 1"));
//        library.addSong(new MP3Song("Song 2", "Artist 2"));
//
//        // Create a new playlist
//        Playlist myPlaylist = new Playlist("My Favorite Songs");
//
//        myPlaylist.addSong(new MP3Song("Song 1", "Artist A"));
//        myPlaylist.addSong(new MP3Song("Song 2", "Artist B"));
//        myPlaylist.addSong(new WAVSong("Song 3", "Artist C"));
//
//        // Set up the MusicController with the library and the playlist
//        MusicController controller = new MusicController();
//        controller.setMusicLibrary(library);
//        controller.setCurrentPlaylist(myPlaylist);
//
//        // Set up the MusicPlayerUI with the controller
//        MusicPlayerUI ui = new MusicPlayerUI(controller);
//        controller.selectSong(0);
//        controller.playSong();
//        ui.displayPlaylist(myPlaylist);
// ------------------------------------------------- Playback Strategy -------------------------------------------------

//        Playlist myPlaylist = new Playlist("Repeat");
//
//        myPlaylist.addSong(new MP3Song("Song 1", "Artist A"));
//        myPlaylist.addSong(new MP3Song("Song 2", "Artist B"));
//        myPlaylist.addSong(new WAVSong("Song 3", "Artist C"));
//
//        // Set the playback strategy to Shuffle and execute it
//        PlaybackStrategy shuffleStrategy = new ShufflePlayback();
//        shuffleStrategy.execute(myPlaylist.getSongs());

    }
}