import java.util.*;

public class Main {

    Lecteur lect;
    ArrayList<Liste> tab = new ArrayList<Liste>();
    DrawCircle dc;

    public static void main(String []args) throws Exception {

        Main m = new Main();
        m.remplir();
        //m.findEquivalence();
        //m.afficherSommets();
        

        PathSearcher ps = new PathSearcher(m.tab);
        m.dc = new DrawCircle(m.tab, 1800, 900, ps);

    }

    Main() throws Exception {
        lect = new Lecteur("test.txt");
        
    }

    public void afficherSommets(){
        for(int i = 0; i < this.tab.size(); i++){
            System.out.print(this.tab.get(i).getOrigin().getValue().getName() + " : ");
            System.out.print(this.tab.get(i).getOrigin().getValue().getStringType());
            Cell cl = this.tab.get(i).getOrigin().getSuivant();
            for(int j = 0; j < this.tab.get(i).lenghtList()-1; j++){
                System.out.print(" -> (" + cl.getRoute().getTypeStr() + " de " 
                    + cl.getRoute().getKm() + "km) " + cl.getValue().getName());
                cl = cl.getSuivant();
            }
            System.out.println("");
        }
    }


    public void remplir() throws Exception {
        
        int nb = lect.nbLine();
        for(int i = 0; i < nb; i++){
            String line = lect.getLine();
            line = line.replace(" ", "");
            String part1 = line.split(":[0-9]|;|:")[0]; // ici pour la localisation
            String part2 = line.split(":;")[1];

            String type = part1.split(",[A-Z]")[0];
            String nom = part1.split(",")[1];
            Double[] pos = {Double.valueOf(part1.split(",")[2]), Double.valueOf(part1.split(",")[3])};

            int typeS = findTypeSm(type);
            Sommet sm = new Sommet(nom, typeS, pos[0], pos[1]);
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
    public void ajoutSommet(){
        Scanner entre = new Scanner(System.in);
        System.out.println("\t Menu Ajout");
        System.out.println("1- Ajouter une ville");
        System.out.println("2- Ajouter un restaurant");
        System.out.println("3- Ajouter un lieu de loisir");
        System.out.println("Que voulez vous faire");
        String lieu;
        int optionmenu =entre.nextInt();
        Sommet sm = new Sommet();
        switch(optionmenu){
            case 2:
                System.out.println("Entrer le nom du restaurant");
                entre.nextLine();
                lieu = entre.nextLine();
                sm = new Sommet(lieu,1 );
                System.out.println("R,"+lieu);
                this.tab.add(new Liste(new Cell(sm)));

                break;
            case 3:
                System.out.println("Entrer le nom du lieu de loisir");
                entre.nextLine();
                lieu = entre.nextLine();
                sm = new Sommet(lieu,2 );
                System.out.println("L,"+lieu);
                this.tab.add(new Liste(new Cell(sm)));
                break;
            default:
                System.out.println("Entrer le nom de la ville");
                entre.nextLine();
                lieu = entre.nextLine();
                sm = new Sommet(lieu,0 );
                System.out.println("V,"+lieu);
                this.tab.add(new Liste(new Cell(sm)));
                break;
        }
        entre.close();
    }

    public void supprimerSommet(){

    }
    public void supprimerRoad(){
        int pos=-1;
        for(int i = 0; i < this.tab.size(); i++){
            if(this.tab.get(i).getOrigin().getValue().getName().equalsIgnoreCase("lyon")){
                pos = i;
            }
        }
        this.tab.get(pos);
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

