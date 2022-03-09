import java.util.*;

public class Main {

    Lecteur lect;
    ArrayList<Liste> tab = new ArrayList<Liste>();
    DrawCircle dc;

    public static void main(String []args) throws Exception {

        Main m = new Main();
        m.remplir();
        m.findEquivalence();
        m.afficherSommets();
        m.dc = new DrawCircle(m.tab, 900, 750);
    }

    Main() throws Exception {
        lect = new Lecteur("data.txt");
        
    }

    public void afficherSommets(){
        for(int i = 0; i < this.tab.size(); i++){
            Cell cl = this.tab.get(i).getOrigin().getSuivant();
            for(int j = 0; j < this.tab.get(i).lenghtList()-1; j++){
                cl = cl.getSuivant();
            }
        }
    }

    public void remplir() throws Exception {
        
        int nb = lect.nbLine();
        for(int i = 0; i < nb; i++){
            String line = lect.getLine();
            line = line.replace(" ", "");
            String part1 = line.split(":[A-Z]|;|:")[0];
            String part2 = line.split("[a-z]:")[1];

            String type = part1.split(",[A-Z]")[0];
            String nom = part1.split("[A-Z],")[1];
            int typeS = findTypeSm(type);
            Sommet sm = new Sommet(nom, typeS);
            Cell linkTab[] = readLink(part2);

            this.tab.add(new Liste(new Cell(sm)));

            for(int j = 0; j < linkTab.length; j++){
                this.tab.get(tab.size()-1).ajouterFin(linkTab[j]);
            }
        }
    }
    public int findTypeSm(String str){
        int typeS;
        switch(str){
            case "R":
                typeS = 1;
                break;
            case "L":
                typeS = 2;
                break;
            default:
                typeS = 0;
                break;
        }
        return typeS;
    }
    public int findTypeRoad(String str){
        int typeS;
        switch(str){
            case "D":
                typeS = 1;
                break;
            case "R":
                typeS = 2;
                break;
            case "N":
                typeS = 3;
                break;
            default:
                typeS = 0;
                break;
        }
        return typeS;
    }

    public Cell[] readLink(String part2){

        int nbLink = part2.split("[a-z];").length;
        Cell linkTabCl[] = new Cell[nbLink];
        for(int i = 0;i < nbLink-1; i++){
            String linkStr = part2.split(";[A-Z]")[i];
            
            String strSm = linkStr.split("::")[1];

            String allRoad = linkStr.split("::")[0];;
            int km = Integer.parseInt(allRoad.split(",")[1]);
            int type = findTypeRoad(allRoad.split(",")[0]);
            strSm = strSm.replace(";", "");
            Sommet linkSm = new Sommet(strSm.split(",")[1], findTypeSm(strSm.split(",")[0]));
            Road route = new Road(type, km);
            linkTabCl[i] = new Cell(linkSm, route);
        }
        return linkTabCl;
    }

    public void findEquivalence(){

        for(int i = 0; i < this.tab.size(); i++){
            
            if(this.tab.get(i).getOrigin().getSuivant() != null){
                Cell cl = this.tab.get(i).getOrigin().getSuivant();

                for(int j = 1; j < this.tab.get(i).lenghtList(); j++){
                    
                    for(int k = 0; k < this.tab.size(); k++){

                        if(this.tab.get(k).getOrigin().getValue().getName().equals(cl.getValue().getName())){
                            this.tab.get(k).getOrigin().setValue(cl.getValue());
                        }
                    }
                    cl = cl.getSuivant();
                }
            }
        }
    }
}
