package View;

import Factory.SongCreator;
import Factory.MP3SongCreator;
import Factory.WAVSongCreator;
import Model.Song; // Make sure this import matches your Song interface's package

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SongView extends JPanel
{
    // GUI components
    private JTextField titleTextField; // Field to enter the song title
    private JTextField artistTextField; // Field to enter the artist's name
    private JButton importMP3Button; // Button to import MP3 files
    private JButton importWAVButton; // Button to import WAV files
    private JButton saveSongButton; // Button to save the current song
    private JLabel filePathLabel; // Label to display the path of the imported file
    private File selectedFile; // File object to keep track of the selected song file
    private String fileType; // String to store the type of the selected file (MP3 or WAV)

    public SongView()
    {
        setLayout(new GridLayout(5, 2, 10, 10)); // Simple grid layout

        // Initialize components
        titleTextField = new JTextField();
        artistTextField = new JTextField();
        importMP3Button = new JButton("Import MP3 Song");
        importWAVButton = new JButton("Import WAV Song");
        saveSongButton = new JButton("Save Song");
        filePathLabel = new JLabel("No file selected");

        // Add components to the panel
        add(new JLabel("Title:"));
        add(titleTextField);
        add(new JLabel("Artist:"));
        add(artistTextField);
        add(importMP3Button);
        add(importWAVButton);
        add(new JLabel("Song File:"));
        add(filePathLabel);
        add(saveSongButton);

        // Setup button actions
        setupActions();
    }

    private void importSong(String fileType, String fileExtension)
    {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType + " Files", fileExtension);
        fileChooser.setFileFilter(filter);
        int option = fileChooser.showOpenDialog(this); // 'this' refers to the JPanel

        if (option == JFileChooser.APPROVE_OPTION)
        {
            selectedFile = fileChooser.getSelectedFile();
            this.fileType = fileType;
            filePathLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    private void saveSong(ActionEvent e)
    {
        String title = titleTextField.getText().trim();
        String artist = artistTextField.getText().trim();

        // Basic validation
        if (title.isEmpty() || artist.isEmpty() || selectedFile == null || fileType == null)
        {
            JOptionPane.showMessageDialog(this, "Please fill all fields and import a song.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Select the correct songCreator based on the file type
        SongCreator songCreator;
        if ("MP3".equals(fileType))
        {
            songCreator = new MP3SongCreator();
        }
        else if ("WAV".equals(fileType))
        {
            songCreator = new WAVSongCreator();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Unsupported file type.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Song song = songCreator.createSong(title, artist, selectedFile.getAbsolutePath());

        // Define the path to the Music folder
        String musicFolderPath = System.getProperty("user.home") + File.separator + "Music";
        File musicFolder = new File(musicFolderPath);
        if (!musicFolder.exists() && !musicFolder.mkdirs())
        {
            JOptionPane.showMessageDialog(this, "Failed to create the music directory.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Use a sanitized title and artist for the filename to avoid filesystem issues
        String sanitizedTitle = title.replaceAll("[^a-zA-Z0-9.-]", "_");
        String sanitizedArtist = artist.replaceAll("[^a-zA-Z0-9.-]", "_");
        String fileName = sanitizedArtist + " - " + sanitizedTitle + (fileType.equals("MP3") ? ".mp3" : ".wav");
        File songFile = new File(musicFolder, fileName);

        try
        {
            Files.copy(Paths.get(selectedFile.getAbsolutePath()), songFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(this, "Song saved successfully: " + song.getDetails(), "Success", JOptionPane.INFORMATION_MESSAGE);

        }
        catch (IOException ioException)
        {
            JOptionPane.showMessageDialog(this, "Error saving the song: " + ioException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void setupActions() {
        importMP3Button.addActionListener(e -> importSong("MP3", "mp3"));
        importWAVButton.addActionListener(e -> importSong("WAV", "wav"));
        saveSongButton.addActionListener(this::saveSong);
    }


}
