package View;

import Factory.SongCreator;
import Factory.AIFFSongCreator;
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
        importMP3Button = new JButton("Import AIFF Song");
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
        // Creates a JFileChooser instance, a GUI mechanism that lets users choose a file from their file system.
        JFileChooser fileChooser = new JFileChooser();


        // Creates a file name extension filter to only show files of a specific type (e.g., AIFF, WAV) in the file chooser dialog.
        // The description and extension are dynamically set based on the method's parameters.
        FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType + " Files", fileExtension);

        // Applies the file extension filter to the file chooser, so only files of the specified type are visible.
        fileChooser.setFileFilter(filter);

        // Displays the file chooser dialog to the user. The dialog is modal and blocks user input to other windows until it's closed.
        // 'this' likely refers to the container component (e.g., a JFrame or JPanel) from which this dialog is spawned.
        int option = fileChooser.showOpenDialog(this); // 'this' refers to the JPanel

        // Checks if the user has selected a file and approved their choice (e.g., by clicking "Open" or "OK").
        if (option == JFileChooser.APPROVE_OPTION)
        {
            // Retrieves the file selected by the user.
            selectedFile = fileChooser.getSelectedFile();

            // Sets the class-level fileType variable to the type of the imported file. This is  used later
            // to handle the file appropriately based on its type.
            this.fileType = fileType;

            // Updates a label in the UI (filePathLabel) to display the absolute path of the selected file.
            // This provides visual feedback to the user about their selection.
            filePathLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    private void saveSong(ActionEvent e)
    {
        // Retrieve and trim input from text fields for the title and artist to remove leading and trailing whitespace.
        String title = titleTextField.getText().trim();
        String artist = artistTextField.getText().trim();

        // Basic validation
        if (title.isEmpty() || artist.isEmpty() || selectedFile == null || fileType == null)
        {
            JOptionPane.showMessageDialog(this, "Please fill all fields and import a song.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Based on the file type (AIFF or WAV), selects the appropriate SongCreator for creating Song objects.
        SongCreator songCreator;
        if ("AIFF".equals(fileType))
        {
            songCreator = new AIFFSongCreator();
        }
        else if ("WAV".equals(fileType))
        {
            songCreator = new WAVSongCreator();
        }
        else
        {
            // If the file type isn't supported, shows an error message and exits.
            JOptionPane.showMessageDialog(this, "Unsupported file type.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Creates a new Song object using the songCreator, with the provided title, artist, and file path.
        Song song = songCreator.createSong(title, artist, selectedFile.getAbsolutePath());

        // Defines the path to a "Music" folder within the user's home directory.
        String musicFolderPath = System.getProperty("user.home") + File.separator + "Music";

        // Attempts to create the music folder if it doesn't already exist.
        File musicFolder = new File(musicFolderPath);

        // If the folder can't be created, shows an error message and exits.
        if (!musicFolder.exists() && !musicFolder.mkdirs())
        {
            JOptionPane.showMessageDialog(this, "Failed to create the music directory.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Sanitizes title and artist for safe file naming by replacing disallowed characters with underscores.
        String sanitizedTitle = title.replaceAll("[^a-zA-Z0-9.-]", "_");
        String sanitizedArtist = artist.replaceAll("[^a-zA-Z0-9.-]", "_");

        // Constructs a filename using sanitized artist and title, appending the correct file extension based on fileType.
        String fileName = sanitizedArtist + " - " + sanitizedTitle + (fileType.equals("AIFF") ? ".aiff" : ".wav");

        // Creates a File object for the new song file in the music folder.
        File songFile = new File(musicFolder, fileName);

        try
        {
            // Copies the selected file to the new location with the constructed filename, replacing any existing file.
            Files.copy(Paths.get(selectedFile.getAbsolutePath()), songFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(this, "Song saved successfully: " + song.getDetails(), "Success", JOptionPane.INFORMATION_MESSAGE);

        }
        catch (IOException ioException)
        {
            JOptionPane.showMessageDialog(this, "Error saving the song: " + ioException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void setupActions() {
        importMP3Button.addActionListener(e -> importSong("AIFF", "aiff"));
        importWAVButton.addActionListener(e -> importSong("WAV", "wav"));
        saveSongButton.addActionListener(this::saveSong);
    }


}
