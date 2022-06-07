package DataStructure;

import DataStructure.NodeType;

import java.net.URL;

public class Sommet {
    
    private String name;
    private NodeType type;
    private int X;
    private int Y;

    private double locX;
    private double locY;
    private URL imageLink;

    public Sommet(){
        this.name = null;
        this.type = NodeType.VILLE;
    }
    public Sommet(String name, NodeType type){
        this.name = name;
        this.type = type;
    }
    public Sommet(String name, NodeType type, double locX, double locY){
        this.name = name;
        this.type = type;
        this.locX = locX;
        this.locY = locY;

        this.X = (int)locX;
    }

    // Getters
    public String getName(){
        return this.name;
    }
    public NodeType getType(){
        return this.type;
    }
    public int getX() {
        return this.X;
    }
    public int getY() {
        return this.Y;
    }

    public double getLocX() {
        return this.locX;
    }

    public void setLocX(double locX) {
        this.locX = locX;
    }

    public double getLocY() {
        return this.locY;
    }

    public void setLocY(double locY) {
        this.locY = locY;
    }
    
    public String getStringType(){
        if(this.type == NodeType.VILLE) return "ville";
        else if(this.type == NodeType.RESTAURANT) return "restaurant";
        else if(this.type == NodeType.LOISIR) return "loisir";
        else if(this.type == NodeType.SERVICE) return "service";
        else return "erreur";

    }
    public char getCharType(){
        if(this.type == NodeType.VILLE) return 'V';
        else if(this.type == NodeType.RESTAURANT) return 'R';
        else if(this.type == NodeType.LOISIR) return 'L';
        else if(this.type == NodeType.SERVICE) return 'S';
        else return 'E';
    }
    // Setters
    public void setName(String name){
        this.name = name;
    }
    public void setType(NodeType type){
        this.type = type;
    }
    public void setX(int X) {
        this.X = X;
    }
    public void setY(int Y) {
        this.Y = Y;
    }
    public String toString(){
        return this.name;
    }
}