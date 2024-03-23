package View;

import Factory.MP3SongCreator;
import Factory.SongCreator;
import Factory.WAVSongCreator;
import Model.MusicLibrary;
import Model.Song;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CreateSong extends JFrame{
    private JTextField titleTextField;
    private JTextField artistTextField;
    private JButton importMP3Button;
    private JButton importWAVButton;
    private JButton saveSongButton;
    private JLabel filePathLabel;
    private File selectedFile;
    private String fileType;

    private MusicLibrary musicLibrary; // Add a MusicLibrary member


    public CreateSong(MusicLibrary library) {

        this.musicLibrary = library;
        JFrame frame = new JFrame("Music Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 2, 10, 10));

        titleTextField = new JTextField();
        artistTextField = new JTextField();
        importMP3Button = new JButton("Import MP3 Song");
        importWAVButton = new JButton("Import WAV Song");
        saveSongButton = new JButton("Save Song");
        filePathLabel = new JLabel("No file selected");

        frame.add(new JLabel("Title:"));
        frame.add(titleTextField);
        frame.add(new JLabel("Artist:"));
        frame.add(artistTextField);
        frame.add(importMP3Button);
        frame.add(importWAVButton);
        frame.add(new JLabel("Song File:"));
        frame.add(filePathLabel);
        frame.add(saveSongButton);

        setupButtonListeners(frame);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setupButtonListeners(JFrame frame) {
        importMP3Button.addActionListener(e -> selectFile(frame, "MP3", "mp3"));
        importWAVButton.addActionListener(e -> selectFile(frame, "WAV", "wav"));
        saveSongButton.addActionListener(e -> saveSong(frame));
    }

    private void selectFile(JFrame frame, String fileType, String fileExtension) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType + " Files", fileExtension);
        fileChooser.setFileFilter(filter);
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            this.fileType = fileType;
            filePathLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    private void saveSong(JFrame frame)
    {
        String title = titleTextField.getText();
        String artist = artistTextField.getText();
        if (fileType != null && !title.isEmpty() && !artist.isEmpty() && selectedFile != null) {
            SongCreator creator = fileType.equals("MP3") ? new MP3SongCreator() : new WAVSongCreator();
            Song song = creator.createSong(title, artist, selectedFile.getAbsolutePath());

            String musicSongFolderPath = "./MusicSong";
            File musicSongFolder = new File(musicSongFolderPath);

            if (!musicSongFolder.exists()) {
                musicSongFolder.mkdir();
            }

            File songFile = new File(musicSongFolder, title.replaceAll("[^a-zA-Z0-9.-]", "_") + (fileType.equals("MP3") ? ".mp3" : ".wav"));
            try {
                Files.copy(selectedFile.toPath(), songFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("New song created: " + song.getDetails() + ", saved to: " + songFile.getAbsolutePath());
                musicLibrary.addSong(song);

                JOptionPane.showMessageDialog(frame, "Song saved: " + song.getDetails());
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(frame, "Error saving the song.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please fill all fields and import a song.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new CreateSong(m).setVisible(true);
//        });
//    }
}
