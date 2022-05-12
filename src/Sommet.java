import java.net.URL;

public class Sommet {
    
    private String name;
    private NodeList.NodeType type; // 0 : ville, 1 : restaurant, 2 : loisir
    private int X;
    private int Y;

    private double locX;
    private double locY;
    private URL imageLink;

    Sommet(){
        this.name = null;
        this.type = NodeList.NodeType.VILLE;
    }
    Sommet(String name, NodeList.NodeType type){
        this.name = name;
        this.type = type;
    }
    Sommet(String name, NodeList.NodeType type, double locX, double locY){
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
    public NodeList.NodeType getType(){
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
        if(this.type == NodeList.NodeType.VILLE) return "ville";
        else if(this.type == NodeList.NodeType.RESTAURANT) return "restaurant";
        else if(this.type == NodeList.NodeType.LOISIR) return "loisir";
        else if(this.type == NodeList.NodeType.SERVICE) return "service";
        else return "erreur";

    }
    public char getCharType(){
        if(this.type == NodeList.NodeType.VILLE) return 'V';
        else if(this.type == NodeList.NodeType.RESTAURANT) return 'R';
        else if(this.type == NodeList.NodeType.LOISIR) return 'L';
        else if(this.type == NodeList.NodeType.SERVICE) return 'S';
        else return 'E';
    }
    // Setters
    public void setName(String name){
        this.name = name;
    }
    public void setType(NodeList.NodeType type){
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