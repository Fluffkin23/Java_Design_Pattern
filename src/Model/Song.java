package Model;

public interface Song
{
    void play();
    void pause();
    String getDetails();
    void setFilePath(String filePath);
    String getTitle(); // Add this method
    String getArtist(); // Add this method

}
