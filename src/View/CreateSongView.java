package View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CreateSongView extends JPanel
{
    private JTextField titleTextField;
    private JTextField artistTextField; // Field to enter the artist's name
    private JButton importAIFFButton; // Button to import AIFF files
    private JButton importWAVButton; // Button to import WAV file
    private JButton saveSongButton;
    private JLabel filePathLabel;
    private String fileType;
    private File selectedFile; // File object to keep track of the selected song file

    public CreateSongView()
    {
        initializeGUI();
    }

    public void initializeGUI()
    {
        setLayout(new GridLayout(5,2,10,10)); // simple grid layout
        titleTextField = new JTextField("Title");
        artistTextField = new JTextField("Artist");
        importAIFFButton = new JButton("Import AIFF Song");
        importWAVButton = new JButton("Import WAV Song");
        saveSongButton = new JButton("Save Song");
        filePathLabel = new JLabel("No file selected");
        addComponentsToPanel();
        setUpActions();
    }

    public void addComponentsToPanel()
    {
        add(titleTextField);
        add(artistTextField);
        add(importAIFFButton);
        add(importWAVButton);
        add(filePathLabel);
        add(saveSongButton);
    }

    public static JFileChooser getjFileChooser(String fileType, String fileExtension)
    {
        JFileChooser fileChooser = new JFileChooser();


        // Creates a file name extension filter to only show files of a specific type (e.g., AIFF, WAV) in the file chooser dialog.
        // The description and extension are dynamically set based on the method's parameters.
        FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType + " Files", fileExtension);

        // Applies the file extension filter to the file chooser, so only files of the specified type are visible.
        fileChooser.setFileFilter(filter);
        return fileChooser;
    }

    public void importSong(String fileType, String fileExtension)
    {
        // Creates a JFileChooser instance, a GUI mechanism that lets users choose a file from their file system.
        JFileChooser fileChooser = getjFileChooser(fileType, fileExtension);

        // Displays the file chooser dialog to the user. The dialog is modal and blocks user input to other windows until it's closed.
        // 'this' likely refers to the container component (e.g., a JFrame or JPanel) from which this dialog is spawned.
        int option = fileChooser.showOpenDialog(this); // 'this' refers to the JPanel

        // Checks if the user has selected a file and approved their choice (e.g., by clicking "Open" or "OK").
        if (option == JFileChooser.APPROVE_OPTION)
        {
            // Retrieves the file selected by the user.
            this.selectedFile = fileChooser.getSelectedFile();

            // Sets the class-level fileType variable to the type of the imported file. This is  used later
            // to handle the file appropriately based on its type.
            this.fileType = fileType;

            // Updates a label in the UI (filePathLabel) to display the absolute path of the selected file.
            // This provides visual feedback to the user about their selection.
            filePathLabel.setText(this.selectedFile.getAbsolutePath());
        }
    }

    // Method to get the Music directory in the user's home folder
    public File getMusicDirectory()
    {
        String musicFolderPath = System.getProperty("user.home") + File.separator + "Music";
        return new File(musicFolderPath);
    }

    // Method to sanitize a string for use as a filename
    public String sanitizeFileName(String input)
    {
        return input.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    //Method to create a filename from the song's artist and title
    public String createFileName(String title, String artist, String fileType)
    {
        String sanitizedTitle = sanitizeFileName(title);
        String sanitizedArtist = sanitizeFileName(artist);
        String extension = fileType.equals("AIFF") ? ".aiff" : ".wav";

        return sanitizedTitle + " - " + sanitizedArtist + extension;
    }

    public void saveSongToFile(File sourceFile, String filename)
    {
        // Create a new File object for the destination by combining the Music directory path and the filename.
        File destinationFile = new File(getMusicDirectory(),filename);
        try
        {
            // Try to copy the file from the source to the destination.
            // The REPLACE_EXISTING option means that if a file with the same name exists, it will be overwritten.
            Files.copy(Paths.get(sourceFile.getAbsolutePath()), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // If the copy operation was successful, display a dialog box to the user indicating success.
            JOptionPane.showMessageDialog(this, "Song saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(this, "Error saving the song: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean ensureMusicDirectoryExist()
    {
        File musicFolder = getMusicDirectory();
        if(!musicFolder.exists() && !musicFolder.mkdir())
        {
            JOptionPane.showMessageDialog(this, "Failed to create the music directory.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void trySaveSong(String title,String artist, File selectedFile, String fileType)
    {
        if(!ensureMusicDirectoryExist())
        {
            return; // Exit if the music directory cannot be created
        }
        String fileName = createFileName(title,artist,fileType);
        saveSongToFile(selectedFile,fileName);
    }

    public void createSongFromFile(ActionEvent e)
    {
        // Retrieve and trim input from text fields for the title and artist to remove leading and trailing whitespace.
        String songTitle = titleTextField.getText().trim();
        String artistName = artistTextField.getText().trim();

        //Basic validation
        if(songTitle.isEmpty() || artistName.isEmpty() || this.selectedFile == null || this.fileType == null)
        {
            JOptionPane.showMessageDialog(this, "Please fill all fields and import a song.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        trySaveSong(songTitle,artistName,this.selectedFile,this.fileType);
    }

    private void setUpActions()
    {
        importAIFFButton.addActionListener(e -> importSong("AIFF", "aiff"));
        importWAVButton.addActionListener(e -> importSong("WAV", "wav"));
        saveSongButton.addActionListener(this::createSongFromFile);
    }

    //---------------Testing Purpose------------------------------------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the frame to hold the CreateSongView panel
            JFrame frame = new JFrame("Create Song");

            // Set the default close operation so the application will shut down when the frame is closed
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create an instance of the CreateSongView panel
            CreateSongView createSongView = new CreateSongView();

            // Add the panel to the frame
            frame.add(createSongView);

            // Pack the frame to respect the preferred sizes of added components
            frame.pack();

            // Optionally, you can set a specific size
            // frame.setSize(new Dimension(400, 300));

            // Center the frame on the screen
            frame.setLocationRelativeTo(null);

            // Make the frame visible
            frame.setVisible(true);
        });
    }
}
