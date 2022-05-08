import java.util.ArrayList;

public class NeighborsList extends ArrayList<Cell> {

    NeighborsList(Cell origine){
         add(origine);
    }

    public ArrayList<Cell> getNeighbors() {
        return new ArrayList<Cell>(this.subList(1, this.size()));
    }
    public Cell getOrigin() {
        return this.get(0);
    }
    public Sommet getSommet() {
        return this.getOrigin().getValue();
    }

}
