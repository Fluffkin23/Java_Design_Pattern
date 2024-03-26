package View;

import javax.swing.*;
import java.awt.*;

public class PlaylistView extends JPanel
{
    public PlaylistView()
    {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));

        JList<String> playlistList = new JList<>(new String[]
                {
                        "Playlist 1", "Playlist 2"
                });
        add(new JScrollPane(playlistList), BorderLayout.CENTER);
    }
}
