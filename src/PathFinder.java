
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PathFinder {
    
    private NodeList tab;

    public PathFinder(NodeList tab){
        this.tab = tab;
    }

    public void initializeDistances(Map<Sommet,Double> distance, Sommet startSm){

        for(int i = 0; i < this.tab.size(); i++){
            Sommet sm = this.tab.getNode(i);
            distance.put(sm, Double.POSITIVE_INFINITY);
        }
        distance.put(startSm, (double) 0);
    }

    private void release(Map<Sommet,Double> distance, Map<Sommet,Sommet> previous, Sommet originNode, Cell currentLink) {
        
        if(distance.get(currentLink.getValue()) > (distance.get(originNode) + currentLink.getRoute().getKm())) {
            distance.put(currentLink.getValue(), distance.get(originNode) + currentLink.getRoute().getKm());

            previous.put(currentLink.getValue(), originNode); // Pb ici, (b, c) visiblement non relaché
        }
   
    }

    public Sommet CellWithMinimalDistance(Map<Sommet,Double> distances, ArrayList<Sommet> restant) {
        Sommet lowerCell = restant.get(0);
        Double lowerDistance = distances.get(lowerCell);
        for (Sommet node : restant) {
            if  (distances.get(node) < lowerDistance) {
                lowerCell = node;
                lowerDistance = distances.get(node);
            }
        }
        return lowerCell;
    }

    public ArrayList<Sommet> dijkstra(Sommet startSm, Sommet endSm){// mode = 0 : chemin le plus court, mode = 1 : chemin le plus rapide

        Map<Sommet,Double> distance = new HashMap<Sommet,Double>();
        Map<Sommet,Sommet> previous = new HashMap<Sommet,Sommet>();
        ArrayList<Sommet> restant = new ArrayList<Sommet>();
        ArrayList<Sommet> chemin = new ArrayList<Sommet>();

        initializeDistances(distance, startSm);
        for(int i = 0; i < tab.size(); i++){
            restant.add(tab.getNode(i));
        }

        while(!restant.isEmpty()){
            Sommet currentSm = CellWithMinimalDistance(distance, restant);
            restant.remove(currentSm);
            if(currentSm.equals(endSm)){
                chemin.add(currentSm);
                while(previous.get(currentSm) != null){
                    chemin.add(previous.get(currentSm));
                    currentSm = previous.get(currentSm);
                }
                break;
            }
            for(int i = 0; i < tab.get(tab.getNodeIndex(currentSm)).size(); i++){
                Cell currentLink = tab.get(tab.getNodeIndex(currentSm)).get(i);
                release(distance, previous, currentSm, currentLink);
            }
        }
        return chemin;
    }

    public Sommet findSmByString(String name){

        for(int i = 0; i < tab.size(); i++){
            if(tab.getNode(i).getName().equals(name))
                return tab.getNode(i);
        }
        return new Sommet();
    }


    // Retourne la ville la plus ouverte à 2 de distances en fonction du mode (0 = ville, 1 = restaurant, ...)
    public Sommet mostOpen(Sommet sm1, Sommet sm2, int mode){

        ArrayList<String> alreadyCheck = new ArrayList<String>();
        int[] counter = {0,0};

        NeighborsList[] ls = {tab.get(tab.getNodeIndex(sm1)),tab.get(tab.getNodeIndex(sm2))};
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < ls[i].size(); j++){
                if(ls[i].get(j).getValue().getType() == mode &&
                    !alreadyCheck.contains(ls[i].get(j).getValue().getName())){
                    alreadyCheck.add((ls[i].get(j).getValue().getName()));
                    counter[i]++;
                }
                NeighborsList liste = tab.get(tab.getNodeIndex(ls[i].get(j).getValue()));
                for(int k = 0; k < liste.size(); k++){
                    if(liste.get(k).getValue().getType() == mode &&
                        !alreadyCheck.contains(liste.get(k).getValue().getName())){
                        alreadyCheck.add((liste.get(k).getValue().getName()));
                        counter[i]++;
                    }
                }
            }
            alreadyCheck = new ArrayList<>();;
        }
        if(counter[0] > counter[1])
            return sm1;
        else if(counter[1] > counter[0])
            return sm2;
        return new Sommet();
    }
}
