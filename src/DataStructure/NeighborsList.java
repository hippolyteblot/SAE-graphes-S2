package DataStructure;

import java.util.ArrayList;

public class NeighborsList extends ArrayList<Cell> {

    public NeighborsList(Cell origin){
         add(origin);
    }

    public ArrayList<Cell> getNeighbors() {
        return new ArrayList<>(this.subList(1, this.size()));
    }
    public Cell getOrigin() {
        return this.get(0);
    }
    public Sommet getSommet() {
        return this.getOrigin().getValue();
    }

}
