package Model;

import Controller.MusicController;

public interface Song
{

    String getDetails();
    void setFilePath(String filePath);
    String getFilePath();
    void play();
    void pause(MusicController controller);


    String getTitle();
    String getArtist();

}
