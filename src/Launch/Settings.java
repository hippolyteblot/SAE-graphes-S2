package Launch;

import java.awt.*;
import java.io.Serializable;

public class Settings implements Serializable {
    private static final int serialVersionUID = 1;
    private int width;
    private int height;
    private double linkFactor;
    private boolean fullscreen;
    private boolean darkMod;

    public Settings() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width;
        height = screenSize.height;
        fullscreen = false;
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

    public void setLinkFactor(double linkFactor){
        this.linkFactor = linkFactor;
    }
    public void setFullScreen(boolean fullscreen){
        this.fullscreen = fullscreen;
    }
    public double getLinkFactor(){
        return linkFactor;
    }

}
