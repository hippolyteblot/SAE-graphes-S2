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
    public Sommet nodeWithMinimalDistance(Map<String,Double> distances, ArrayList<Sommet> remaining) {
        Sommet minimalNode = remaining.get(0);
        Double minimalDistance = distances.get(minimalNode.getName());
        for (Sommet node : remaining) {
            if  (distances.get(node.getName()) < minimalDistance) {
                minimalNode = node;
                minimalDistance = distances.get(node.getName());
            }
        }
        return minimalNode;
    }

    public ArrayList<String> dijkstra(Sommet StartSm, Sommet EndSm){

        ArrayList<String> path = new ArrayList<>();
        ArrayList<Sommet> remaining = new ArrayList<Sommet>();
        for(int i = 0; i < tab.size(); i++){
            remaining.add(tab.get(i).getOrigin().getValue());
        }
        Map<String,Double> distance = new HashMap<>();
        Map<String,String> previous = new HashMap<>();
        Sommet minimalNode;
        String actuel;
        initializeDistances(distance, StartSm);
        previous.put(StartSm.getName(), null);

        while (!remaining.isEmpty()){
            minimalNode = nodeWithMinimalDistance(distance, remaining);
            
            remaining.remove(tab.get(indexSm2(minimalNode)).getOrigin().getValue());
            Cell tmp = tab.get(indexSm2(minimalNode)).getOrigin();
            

            for(int i = 0; i < tab.get(indexSm2(minimalNode)).lenghtList(); i++){
                release(distance, previous, minimalNode, tmp);
                tmp = tmp.getSuivant();
            }
        }

        if (distance.get(EndSm.getName()) != Double.POSITIVE_INFINITY){
            
            path.add(EndSm.getName());
            actuel = EndSm.getName();
            
            while(previous.get(actuel) != null){
                path.add(0, previous.get(actuel));
                actuel = previous.get(actuel);
            }
        }
        
        return path;
    }

    public int indexSm(Sommet sm, ArrayList<Sommet> remaining){

        int index = 0;
        for(int i = 0; i < remaining.size(); i++){
            if(sm.getName().equals(remaining.get(i).getName()))
                index = i;
        }
        return index;
    }

    public int indexSm2(Sommet sm){

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

        Liste[] ls = {tab.get(indexSm2(sm1)),tab.get(indexSm2(sm2))};
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < ls[i].lenghtList(); j++){
                if(ls[i].getACell(j).getValue().getType() == mode &&
                    !alreadyCheck.contains(ls[i].getACell(j).getValue().getName())){
                    alreadyCheck.add((ls[i].getACell(j).getValue().getName()));
                    counter[i]++;
                }
                Liste liste = tab.get(indexSm2(ls[i].getACell(j).getValue()));
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
