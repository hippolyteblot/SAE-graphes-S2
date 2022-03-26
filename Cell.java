
public class Cell {
    
    private Sommet value;
    private Cell suivant;
    private Road route;

    Cell(){
        this.value = new Sommet();
        this.route = new Road();
    }
    Cell(Sommet sommet){
        this.value = sommet;
        this.route = new Road();
    }
    Cell(Sommet sommet, Road route){
        this.value = sommet;
        this.route = route;
    }
    // Setters
    public Sommet getValue(){
        return this.value;
    }
    public Cell getSuivant(){
        return this.suivant;
    }
    public Road getRoute() {
        return this.route;
    }
    // Setters
    public void setValue(Sommet value){
        this.value = value;
    }
    public void setSuivant(Cell suivant){
        this.suivant = suivant;
    }
    public void setRoute(Road route) {
        this.route = route;
    }
}
