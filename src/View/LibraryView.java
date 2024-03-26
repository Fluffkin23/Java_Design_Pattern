package View;

import javax.swing.*;
import java.awt.*;

public class LibraryView extends JPanel
{
    public LibraryView()
    {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));

        JList<String> songList = new JList<>(new String[]
                {
                        "Song 1", "Song 2", "Song 3"
                });
        add(new JScrollPane(songList), BorderLayout.CENTER);
    }
}
