package Model;

import Controller.MusicController;

public interface Song
{

    String getDetails();
    void setFilePath(String filePath);
    void play(MusicController controller);
    void pause(MusicController controller);


    String getTitle();
    String getArtist();

}
