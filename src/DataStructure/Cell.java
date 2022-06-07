package DataStructure;

import DataStructure.Road;

public class Cell {
    
    private Sommet value;
    private Road route;

    Cell(){
        this.value = new Sommet();
        this.route = new Road();
    }
    public Cell(Sommet sommet){
        this.value = sommet;
        this.route = new Road();
    }
    public Cell(Sommet sommet, Road route){
        this.value = sommet;
        this.route = route;
    }
    // Setters
    public Sommet getValue(){
        return this.value;
    }
    public Road getRoute() {
        return this.route;
    }
    // Setters
    public void setValue(Sommet value){
        this.value = value;
    }
    public void setRoute(Road route) {
        this.route = route;
    }
}
