import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PathSearcher {
    
    private ArrayList<Liste> tab;

    public PathSearcher(ArrayList<Liste> tab){
        this.tab = tab;
    }

    public void initializeDistances(Map<String,Double> distance, Sommet startSm){

        for(int i = 0; i < this.tab.size(); i++){
            Sommet sm = this.tab.get(i).getOrigin().getValue();
            distance.put(sm.getName(), Double.POSITIVE_INFINITY);
        }
        distance.put(startSm.getName(), (double) 0);
    }

    private void release(Map<String,Double> distance, Map<String,String> previous, Sommet originNode, Cell currentLink) {
        
        if(distance.get(currentLink.getValue().getName()) > (distance.get(originNode.getName()) + currentLink.getRoute().getKm())) {
            distance.put(currentLink.getValue().getName(), distance.get(originNode.getName()) + currentLink.getRoute().getKm());

            //System.out.println("Arc Ajoute : (" + originNode.getName() + ", " + currentLink.getValue().getName() + ")");

            previous.put(currentLink.getValue().getName(), originNode.getName()); // Pb ici, (b, c) visiblement non relaché
        }
   
    }
    public Sommet CellWithMinimalDistance(Map<String,Double> distances, ArrayList<Sommet> restant) {
        Sommet lowerCell = restant.get(0);
        Double lowerDistance = distances.get(lowerCell.getName());
        for (Sommet node : restant) {
            if  (distances.get(node.getName()) < lowerDistance) {
                lowerCell = node;
                lowerDistance = distances.get(node.getName());
            }
        }
        return lowerCell;
    }

    public ArrayList<String> dijkstra(Sommet StartSm, Sommet EndSm){

        ArrayList<String> path = new ArrayList<>();
        ArrayList<Sommet> restant = new ArrayList<Sommet>();
        for(int i = 0; i < tab.size(); i++){
            restant.add(tab.get(i).getOrigin().getValue());
        }
        Map<String,Double> distance = new HashMap<>();
        Map<String,String> precedent = new HashMap<>();
        Sommet minimalNode;
        String actuel;
        initializeDistances(distance, StartSm);
        precedent.put(StartSm.getName(), null);

        while (!restant.isEmpty()){
            minimalNode = CellWithMinimalDistance(distance, restant);
            
            restant.remove(tab.get(indexSm(minimalNode)).getOrigin().getValue());
            Cell tmp = tab.get(indexSm(minimalNode)).getOrigin();
            

            for(int i = 0; i < tab.get(indexSm(minimalNode)).lenghtList(); i++){
                release(distance, precedent, minimalNode, tmp);
                tmp = tmp.getSuivant();
            }
        }

        if (distance.get(EndSm.getName()) != Double.POSITIVE_INFINITY){
            
            path.add(EndSm.getName());
            actuel = EndSm.getName();
            
            while(precedent.get(actuel) != null){
                path.add(0, precedent.get(actuel));
                actuel = precedent.get(actuel);
            }
        }
        
        return path;
    }

    public int indexSm(Sommet sm){

        int index = 0;
        for(int i = 0; i < tab.size(); i++){
            if(sm.getName().equals(tab.get(i).getOrigin().getValue().getName()))
                index = i;
        }
        return index;
    }

    public Sommet findSmByString(String name){

        for(int i = 0; i < tab.size(); i++){
            if(tab.get(i).getOrigin().getValue().getName().equals(name))
                return tab.get(i).getOrigin().getValue();
        }
        return new Sommet();
    }


    // Retourne la ville la plus ouverte à 2 de distances en fonction du mode (0 = ville, 1 = restaurant, ...)
    public Sommet mostOpen(Sommet sm1, Sommet sm2, int mode){

        ArrayList<String> alreadyCheck = new ArrayList<String>();
        int[] counter = {0,0};

        Liste[] ls = {tab.get(indexSm(sm1)),tab.get(indexSm(sm2))};
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < ls[i].lenghtList(); j++){
                if(ls[i].getACell(j).getValue().getType() == mode &&
                    !alreadyCheck.contains(ls[i].getACell(j).getValue().getName())){
                    alreadyCheck.add((ls[i].getACell(j).getValue().getName()));
                    counter[i]++;
                }
                Liste liste = tab.get(indexSm(ls[i].getACell(j).getValue()));
                for(int k = 0; k < liste.lenghtList(); k++){
                    if(liste.getACell(k).getValue().getType() == mode &&
                        !alreadyCheck.contains(liste.getACell(k).getValue().getName())){
                        alreadyCheck.add((liste.getACell(k).getValue().getName()));
                        counter[i]++;
                    }
                }
            }
            alreadyCheck = new ArrayList<String>();;
        }
        if(counter[0] > counter[1])
            return sm1;
        else if(counter[1] > counter[0])
            return sm2;
        return new Sommet();
    }
}
