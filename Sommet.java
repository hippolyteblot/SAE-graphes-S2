
public class Sommet {
    
    private String name;
    private int type; // 0 : ville, 1 : restaurant, 2 : loisir

    Sommet(){
        this.name = "Habitation";
        this.type = 0; 
    }
    Sommet(String name, int type){
        this.name = name;
        this.type = type;
    }
    // Getters
    public String getName(){
        return this.name;
    }
    public int getType(){
        return this.type;
    }
    public String getStringType(){
        if(this.type == 0) return "ville";
        else if(this.type == 1) return "restaurant";
        else if(this.type == 2) return "loisir";
        else return "erreur";

    }
    // Setters
    public void setName(String name){
        this.name = name;
    }
    public void setType(int type){
        this.type = type;
    }
}
