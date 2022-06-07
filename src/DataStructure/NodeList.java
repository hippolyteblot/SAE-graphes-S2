package DataStructure;

import java.util.ArrayList;

public class NodeList extends ArrayList<NeighborsList> {

    public NodeList(Cell cell) {
        add(new NeighborsList(cell));
    }
    public NodeList(){}

    public Sommet getNode(int i){
        return get(i).getOrigin().getValue();
    }
    public int getNodeIndex(Sommet s){
        for(int i=0;i<size();i++){
            if(getNode(i).equals(s)){
                return i;
            }
        }
        return -1;
    }
    public void addNode(Sommet s){
        add(new NeighborsList(new Cell(s)));
    }

    public int getNbNeighbors(Sommet s){
        return get(getNodeIndex(s)).getNeighbors().size();
    }
    public Sommet getNodeByName(String name){
        for(int i=0;i<size();i++){
            if(getNode(i).getName().replace(" ", "").equals(name.replace(" ", ""))){
                return getNode(i);
            }
        }
        return null;
    }

    public void add(NodeList neighborsLists) {
        for (NeighborsList neighborsList : neighborsLists) {
            add(neighborsList);
        }
    }

    public ArrayList<Sommet> getAllNodes(){
        ArrayList<Sommet> nodes = new ArrayList<>();
        for(int i=0;i<size();i++){
            nodes.add(getNode(i));
        }
        return nodes;
    }
    public ArrayList<Sommet> getAllNodesOfType(NodeType type){
        ArrayList<Sommet> nodes = new ArrayList<>();
        for(int i=0;i<size();i++){
            if(getNode(i).getType().equals(type)){
                System.out.println("added");
                nodes.add(getNode(i));
            }
        }
        return nodes;
    }
    public void removeListByOrigin(Sommet s){
        remove(getNodeIndex(s));
    }
}
