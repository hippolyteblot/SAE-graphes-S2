
public class Liste {
    
    private Cell origine;

    Liste(){
        this.origine = new Cell();
    }
    Liste(Cell origine){
        this.origine = origine;
    }
    public boolean estVide(){
        if(this.origine != null) return true;
        return false;
    }
    public void ajouterFin(Sommet newSommet){
        Cell actuel = this.origine;
        while(actuel.getSuivant() != null){
            actuel = actuel.getSuivant();
        }
        actuel.setSuivant(new Cell(newSommet));
    }
    public void ajouterFin(Cell newCell){
        Cell actuel = this.origine;
        while(actuel.getSuivant() != null){
            actuel = actuel.getSuivant();
        }
        actuel.setSuivant(newCell);
    }
    public int lenghtList(){
        int count = 1;
        Cell actuel = this.origine;
        while(actuel.getSuivant() != null){
            actuel = actuel.getSuivant();
            count++;
        }
        return count;
    }
    public void supprimer(int indice){

    }
    
    // Getters
    public Cell getOrigin(){
        return this.origine;
    }
    // Setters
    public void setNOrigin(Cell origine){
        this.origine = origine;
    }
}
