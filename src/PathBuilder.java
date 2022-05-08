import java.util.ArrayList;
import java.util.Collection;

public class PathBuilder {

    private NodeList tab;

    public PathBuilder(NodeList tab) {
        this.tab = tab;
    }

    public Sommet findNearestNeighbor(Sommet s) {
        Sommet nearest = null;
        double distanceMin = Double.MAX_VALUE;
        for (NeighborsList ls : tab) {
            if(!ls.getOrigin().getValue().equals(s)) {
                if (distance(ls.getOrigin().getValue(), s) < distanceMin) {
                    distanceMin = distance(ls.getOrigin().getValue(), s);
                    nearest = ls.getOrigin().getValue();
                }
            }
        }
        return nearest;
    }
    public Sommet findNearestNeighbor(Sommet s, ArrayList<Sommet> nodes){
        // Construction d'une liste de cellules contenant les sommets de la liste nodes
        ArrayList<NeighborsList> list = new ArrayList<>();
        for(Sommet s1 : nodes){
            list.add(tab.get(tab.getNodeIndex(s1)));
        }

        Sommet nearest = null;
        double distanceMin = Double.MAX_VALUE;
        for (NeighborsList ls : list) {
            if(!ls.getOrigin().getValue().equals(s)) {
                if(distance(ls.getOrigin().getValue(), s) < distanceMin) {
                    distanceMin = distance(ls.getOrigin().getValue(), s);
                    nearest = ls.getOrigin().getValue();
                }
            }
        }
        return nearest;
    }
    public ArrayList<Sommet> linkedSm(Sommet s){
        Sommet nearest = findNearestNeighbor(s);
        Double distance = distance(nearest,s);
        Double radius = distance*1.5;
        ArrayList<Sommet> linked = new ArrayList<>();
        for (NeighborsList ls : tab) {
            if(distance(ls.getOrigin().getValue(),s)<=radius && !ls.getOrigin().getValue().equals(s)){
                linked.add(ls.getOrigin().getValue());
            }
        }
        return linked;
    }
    public void addLink(Sommet s, Sommet s1){ // TODO
        boolean found = false;
        for (int i = 1; i < tab.get(tab.getNodeIndex(s)).size(); i++) {
            if(tab.get(tab.getNodeIndex(s)).get(i) == null){
                tab.get(tab.getNodeIndex(s)).set(i, new Cell(s1, new Road(0, (int) Math.round(10000*distance(s,s1)))));
                found = true;
            }
        }
        if(!found){
            tab.get(tab.getNodeIndex(s)).add(new Cell(s1, new Road(0, (int) Math.round(10000*distance(s,s1)))));
        }
    }
    public void addAllLinks(Sommet s){
        for (Sommet s1 : linkedSm(s)) {
            if(!existingLink(s,s1)) {
                addLink(s, s1);
                System.out.println("Lien ajouté : "+s.getName()+" "+s1.getName());
            }
        }
    }
    public void buildEveryLinks(){
        for (NeighborsList ls : tab) {
            addAllLinks(ls.getSommet());
        }
    }
    public double distance(Sommet s1, Sommet s2){
        return Math.sqrt(Math.pow(s1.getLocX()-s2.getLocX(),2)+Math.pow(s1.getLocY()-s2.getLocY(),2));
    }
    public void syncronize(){
        for(NeighborsList ls : tab){
            for(int i = 1; i < ls.size(); i++){
                int linkedIndex = tab.getNodeIndex(ls.get(i).getValue());
                boolean found = false;
                if(linkedIndex != -1){
                    for(int j = 0; j < tab.get(linkedIndex).size(); j++){
                        if (tab.get(linkedIndex).get(j).getValue().equals(ls.getSommet())){
                            found = true;
                        }
                    }
                    if(!found){
                        tab.get(linkedIndex).add(new Cell(ls.getSommet(),
                                new Road(0, (int) Math.round(10000*distance(tab.get(linkedIndex).getSommet(),
                                        ls.getSommet()))))); // TODO : Pourquoi 10000 ?
                    }
                }

            }
        }
    }
    public void makeRelate(){ // TODO : Probleme au niveau de la distance (visiblement sur prime)
        ArrayList<Sommet> spanningTree = prim();
        for(int i = 0; i < spanningTree.size()-1; i++){
            if(!existingPath(spanningTree.get(i), spanningTree.get(i+1))){
                addLink(spanningTree.get(i), spanningTree.get(i+1));
            }
        }
    }

    public ArrayList<Sommet> prim(){
        ArrayList<Sommet> spanningTree = new ArrayList<>();
        if(tab.size()>0){
            Sommet s = tab.get(0).getSommet();
            spanningTree.add(s);
            while(spanningTree.size()<tab.size()){

                ArrayList<Sommet> restant = subListToList(tab.getAllNodes() ,spanningTree);

                Sommet nearest = findNearestNeighbor(s, restant);
                spanningTree.add(nearest);
                s = nearest;
            }
        }

        return spanningTree;
    }

    public ArrayList<Sommet> subListToList(ArrayList<Sommet> list, ArrayList<Sommet> sublist){
        ArrayList<Sommet> restant = new ArrayList<>();
        for(Sommet s : list){
            if(!sublist.contains(s)){
                restant.add(s);
            }
        }
        return restant;
    }

    public ArrayList<Sommet> kurskal(){
        ArrayList<Sommet> kruskal = new ArrayList<>();
        ArrayList<Sommet> checked = new ArrayList<>();
        ArrayList<ArrayList<Sommet>> componentList = new ArrayList<>();
        ArrayList<Sommet> component = new ArrayList<>();
        for (NeighborsList ls : tab) {
            if(!checked.contains(ls.getSommet())){
                component = connectedComponent(ls.getSommet(), checked);
                componentList.add(component);
            }
        }
        for (ArrayList<Sommet> component1 : componentList) {
            kruskal.addAll(component1);
        }
        return kruskal;
    }
    public ArrayList<Sommet> connectedComponent(Sommet s, ArrayList<Sommet> checked){
        ArrayList<Sommet> component = new ArrayList<>();
        component.add(s);
        checked.add(s);
        for (int i = 0; i < tab.get(tab.getNodeIndex(s)).size(); i++) {
            if(!checked.contains(tab.get(tab.getNodeIndex(s)).get(i).getValue())){
                component.addAll(connectedComponent(tab.get(tab.getNodeIndex(s)).get(i).getValue(), checked));
            }
        }
        return component;
    }



    public ArrayList<Sommet> getAllNodes(){
        ArrayList<Sommet> nodes = new ArrayList<>();
        for (NeighborsList ls : tab) {
            nodes.add(ls.getSommet());
        }
        return nodes;
    }
    public boolean existingPath(Sommet s1, Sommet s2){
        PathFinder pf = new PathFinder(tab);
        return (pf.dijkstra(s1,s2).size() > 1);
    }
    public ArrayList<ArrayList<Sommet>> buildComponents(){
        ArrayList<ArrayList<Sommet>> components = new ArrayList<>();
        ArrayList<Sommet> checked = new ArrayList<>();
        boolean found = false;
        for (NeighborsList ls : tab) {
            // Trouver si le sommet n'est pas deja dans une composante
            ArrayList<Sommet> representant = new ArrayList<>();
            for (ArrayList<Sommet> component : components) {
                if(component.contains(ls.getSommet())){
                    representant = component;
                    found = true;
                }
            }
            if(!found){
                representant.add(ls.getSommet());
                components.add(representant);
            }
            found = false;


            for(NeighborsList ls2 : tab){
                for(ArrayList<Sommet> component : components){ // Deja traité ?
                    if(component.contains(ls2.getSommet())){
                        found = true;
                    }
                }
                if(!found){ // Si non
                    if(existingPath(ls.getSommet(), ls2.getSommet())){
                        // Ajouter ls2.getSommet() a la composante de ls.getSommet()
                    }
                    else{
                        // Creer une nouvelle composante pour ls2.getSommet()
                        ArrayList tmp = new ArrayList<Sommet>();
                        tmp.add(ls2.getSommet());
                        components.add(tmp);
                    }

                }
            }
        }
        return components;
    }

    public boolean existingLink(Sommet s1, Sommet s2){
        for (NeighborsList ls : tab) {
            if(ls.getSommet().equals(s1)){
                for (Cell cell : ls.getNeighbors()) {
                    if(cell != null && cell.getValue().equals(s2)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public NodeList getTab() {
        return tab;
    }
}
