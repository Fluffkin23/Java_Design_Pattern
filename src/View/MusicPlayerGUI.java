package View;
import Factory.MP3SongCreator;
import Factory.SongCreator;
import Factory.WAVSongCreator;
import Model.Song;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MusicPlayerGUI
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

    public MusicPlayerGUI() {
        // Create the frame
        JFrame frame = new JFrame("Music Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 2, 10, 10)); // Simple grid layout

        // Initialize components
        titleTextField = new JTextField();
        artistTextField = new JTextField();
        importMP3Button = new JButton("Import MP3 Song");
        importWAVButton = new JButton("Import WAV Song");
        saveSongButton = new JButton("Save Song");
        filePathLabel = new JLabel("No file selected");

        // Add components to the frame
        frame.add(new JLabel("Title:"));
        frame.add(titleTextField);
        frame.add(new JLabel("Artist:"));
        frame.add(artistTextField);
        frame.add(importMP3Button);
        frame.add(importWAVButton);
        frame.add(new JLabel("Song File:"));
        frame.add(filePathLabel);
        frame.add(saveSongButton);

        // Add action listener for MP3 import button
        importMP3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a file chooser dialog to select a file
                JFileChooser fileChooser = new JFileChooser();

                // Set up a filter to show only MP3 files in the dialog
                FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 Files", "mp3");
                fileChooser.setFileFilter(filter);

                // Display the file chooser dialog and capture the user's selection
                int option = fileChooser.showOpenDialog(frame);

                // Check if the user has approved (selected a file) from the file chooser
                if (option == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    selectedFile = fileChooser.getSelectedFile();

                    // Set the file type as MP3, used later to determine the correct SongCreator to use
                    fileType = "MP3";

                    // Update the label on the GUI to show the path of the selected file
                    filePathLabel.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        // Add action listener for WAV import button
        importWAVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instantiate a JFileChooser object to open a file dialog.
                JFileChooser fileChooser = new JFileChooser();

                // Create a file filter for .wav files to be used in the file chooser.
                FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV Files", "wav");

                // Apply the WAV file filter to the file chooser, so it only shows WAV files.
                fileChooser.setFileFilter(filter);

                // Display the file chooser dialog to the user, parented to 'frame'.
                int option = fileChooser.showOpenDialog(frame);

                // Check if the user has selected a file ('APPROVE_OPTION') from the file chooser.
                if (option == JFileChooser.APPROVE_OPTION) {
                    // Assign the selected file to the 'selectedFile' variable.
                    selectedFile = fileChooser.getSelectedFile();

                    // Set the 'fileType' variable to "WAV" indicating the type of file chosen.
                    fileType = "WAV";

                    // Update the 'filePathLabel' to display the path of the selected WAV file.
                    filePathLabel.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        // Add action listener for save button
        saveSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the title entered in the title text field.
                String title = titleTextField.getText();
                // Retrieve the artist name entered in the artist text field.
                String artist = artistTextField.getText();
                // Retrieve the file path of the selected file or set an empty string if no file is selected.
                String filePath = selectedFile != null ? selectedFile.getAbsolutePath() : "";

                // Check if the fileType has been set and that title, artist, and filePath are not empty.
                if (fileType != null && !title.isEmpty() && !artist.isEmpty() && !filePath.isEmpty()) {
                    // Initialize the factory creators for MP3 and WAV songs.
                    SongCreator mp3SongCreator = new MP3SongCreator();
                    SongCreator wavSongCreator = new WAVSongCreator();

                    // Declare a variable to hold the created song.
                    Song song;
                    // Check the fileType and use the corresponding creator to create the song.
                    if ("MP3".equals(fileType)) {
                        song = mp3SongCreator.createSong(title, artist, filePath);
                    } else {
                        song = wavSongCreator.createSong(title, artist, filePath);
                    }

                    // Define the path to the Music folder in the user's home directory.
                    String musicFolderPath = System.getProperty("user.home") + File.separator + "Music";
                    // Create a File object for the Music folder.
                    File musicFolder = new File(musicFolderPath);
                    // Check if the Music folder exists, and if not, create it.
                    if (!musicFolder.exists()) {
                        musicFolder.mkdir();
                    }

                    // Sanitize the title to remove any characters that are not letters, numbers, dots, or hyphens.
                    // Also, append the correct file extension based on the fileType.
                    File songFile = new File(musicFolder, title.replaceAll("[^a-zA-Z0-9.-]", "_") + (fileType.equals("MP3") ? ".mp3" : ".wav"));
                    try {
                        // Copy the imported file to the new location in the Music folder.
                        Files.copy(Paths.get(filePath), songFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        // Print to the console the details of the saved song and its location.
                        System.out.println("New song created: " + song.getDetails() + ", saved to: " + songFile.getAbsolutePath());
                        // Show a dialog message confirming the song has been saved.
                        JOptionPane.showMessageDialog(frame, "Song saved: " + song.getDetails());
                    } catch (IOException ioException) {
                        // If there's an error during file copying, show an error dialog.
                        JOptionPane.showMessageDialog(frame, "Error saving the song.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // If not all fields are filled, show an error dialog prompting the user to fill all fields.
                    JOptionPane.showMessageDialog(frame, "Please fill all fields and import a song.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Finalize frame setup
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    // Main method to start the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MusicPlayerGUI();
            }
        });
    }

}
