package Model;

import Controller.MusicController;

public interface Song
{

    String getDetails();
    void setFilePath(String filePath);
    String getFilePath();
    void play();
    void pause();
    void stop();


    String getTitle();
    String getArtist();

}
