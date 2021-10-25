package me.maxrenner.managers;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Static SWING methods
public class WindowManager {

    @Getter private final ArrayList<JFrame> windows; // Used to allow for multiple frames to load on the side of the main one. doubt ill ever do this tho

    public WindowManager(){
        windows = new ArrayList<>();
    }

    public void build(String title, int width, int height, Canvas canvas) {
        JFrame frame = new JFrame(title);

        canvas.setPreferredSize(new Dimension(width, height));
        frame.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);

        frame.setVisible(true);
    }
}
