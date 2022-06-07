package Launch;

import com.formdev.flatlaf.FlatLaf;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Settings implements Serializable {
    private static final int serialVersionUID = 1;
    private int width;
    private int height;
    private double linkFactor;
    private boolean fullscreen;
    private int queryCount;
    private boolean darkMod;

    public Settings() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width;
        height = screenSize.height;
        fullscreen = false;
        queryCount = 0;
        linkFactor = 1.5;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public void setDarkMod(boolean darkMod) {
        this.darkMod = darkMod;
    }
    public boolean isDarkMod() {
        return darkMod;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }
    public void setLinkFactor(double linkFactor){
        this.linkFactor = linkFactor;
    }
    public void setFullScreen(boolean fullscreen){
        this.fullscreen = fullscreen;
    }
    public double getLinkFactor(){
        return linkFactor;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public void incrementQueryCount() {
        queryCount++;
    }
}
