package ProcessingEngine;

import DataStructure.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
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

        Double value;
        if((value = distance.get(currentLink.getValue())) == null)
            value = Double.POSITIVE_INFINITY;

        if(value > (distance.get(originNode) + currentLink.getRoute().getKm())) {
            distance.put(currentLink.getValue(), distance.get(originNode) + currentLink.getRoute().getKm());

            previous.put(currentLink.getValue(), originNode);
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
        ArrayList<Sommet> restant = new ArrayList<>();
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


    // Retourne la ville la plus ouverte à 2 de distances en fonction du mode
    public Sommet mostOpen(Sommet sm1, Sommet sm2, NodeType mode){

        ArrayList<Sommet> alreadyCheck = new ArrayList<Sommet>();
        int[] counter = {0,0};

        NeighborsList[] ls = {tab.get(tab.getNodeIndex(sm1)),tab.get(tab.getNodeIndex(sm2))};
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < ls[i].size(); j++){
                if(ls[i].get(j).getValue().getType() == mode &&
                    !alreadyCheck.contains(ls[i].get(j).getValue())){
                    alreadyCheck.add((ls[i].get(j).getValue()));
                    counter[i]++;
                }
                NeighborsList liste = tab.get(tab.getNodeIndex(ls[i].get(j).getValue()));
                for (Cell cell : liste) {
                    if (cell.getValue().getType() == mode &&
                            !alreadyCheck.contains(cell.getValue())) {
                        alreadyCheck.add((cell.getValue()));
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
        return new Sommet("Aucun", NodeType.VILLE);
    }
    public Road getRoad(Sommet sm1, Sommet sm2){
        NeighborsList list = tab.get(tab.getNodeIndex(sm1));
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getValue().equals(sm2))
                return list.get(i).getRoute();
        }
        return null;
    }

    public double getDistance(Sommet sm1, Sommet sm2){
        ArrayList<Sommet> chemin = dijkstra(sm1, sm2);
        double distance = 0;
        for(int i = 0; i < chemin.size()-1; i++){
            Road kmRoad = getRoad(chemin.get(i), chemin.get(i+1));
            if(kmRoad != null)
                distance += kmRoad.getKm();
            else
                distance += 999999;
        }
        return distance;
    }

    public ArrayList<Sommet> getNeighbors(Sommet sm, int TTL){
        TTL--;
        ArrayList<Sommet> neighbors = new ArrayList<>();
        NeighborsList list = tab.get(tab.getNodeIndex(sm));
        for(int i = 0; i < list.size(); i++){
            if(!neighbors.contains(list.get(i).getValue()))
                neighbors.add(list.get(i).getValue());
            if(TTL > 0){
                neighbors.addAll(getNeighbors(list.get(i).getValue(), TTL));
            }
        }
        LinkedHashSet<Sommet> set = new LinkedHashSet<>(neighbors);
        neighbors.clear();
        neighbors.addAll(set);
        neighbors.remove(sm);
        return neighbors;
    }

    public Sommet getCloserNode(Sommet start, Sommet end, NodeType type){

        Sommet closer = new Sommet();
        ArrayList<Sommet> typeNode = tab.getAllNodesOfType(type);
        typeNode.remove(start);
        typeNode.remove(end);
        ArrayList<Double> distanceFromStart = new ArrayList<>();
        ArrayList<Double> distanceFromEnd = new ArrayList<>();
        for(Sommet sm : typeNode){
            distanceFromStart.add(getDistance(start, sm));
        }
        for(Sommet sm : typeNode){
            distanceFromEnd.add(getDistance(end, sm));
        }
        Double min = Double.MAX_VALUE;
        for(int i = 0; i < typeNode.size(); i++){
            if(distanceFromStart.get(i) + distanceFromEnd.get(i) < min){
                min = distanceFromStart.get(i) + distanceFromEnd.get(i);
                closer = typeNode.get(i);
            }
        }
        return closer;

    }
}
