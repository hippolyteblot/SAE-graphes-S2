import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    Lecteur lect;
    ArrayList<Liste> tab = new ArrayList<Liste>();
    DrawCircle dc;
    
    Main() throws Exception {
        lect = new Lecteur("data.txt");
        
    }
    public static void main(String []args) throws Exception {

        Main m = new Main();
        m.remplir();
        m.findEquivalence();
        //m.afficherSommets();
        

        PathSearcher ps = new PathSearcher(m.tab);
        //m.dc = new DrawCircle(m.tab, 900, 750, ps);

        Sommet sm1 = m.tab.get(0).getOrigin().getValue();
        Sommet sm2 = m.tab.get(9).getOrigin().getValue();

        System.out.println("Qui ? - " + sm1.getName() + " " + sm2.getName());
        System.out.println(ps.mostOpen(sm1, sm2, 2).getName());
        

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
        //lect = new Lecteur("data.txt");
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
    public void ajoutSommet() throws IOException{
        String lieu = "Habitation";
        Scanner entre = new Scanner(System.in);
        System.out.println("\t Menu Ajout");
        System.out.println("1- Ajouter une ville");
        System.out.println("2- Ajouter un restaurant");
        System.out.println("3- Ajouter un lieu de loisir");
        System.out.println("Que voulez vous faire");
        FileWriter fw = new FileWriter("data.txt",true);
        
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
                fw.write("\nR,"+lieu+":;;");
                break;
            case 3:
                System.out.println("Entrer le nom du lieu de loisir");
                entre.nextLine();
                lieu = entre.nextLine();
                sm = new Sommet(lieu,2 );
                System.out.println("L,"+lieu);
                this.tab.add(new Liste(new Cell(sm)));
                fw.write("\nL,"+lieu+":;;");
                break;
            default:
                System.out.println("Entrer le nom de la ville");
                entre.nextLine();
                lieu = entre.nextLine();
                sm = new Sommet(lieu,0 );
                System.out.println("V,"+lieu);
                this.tab.add(new Liste(new Cell(sm)));
                fw.write("\nR,"+lieu+":;;");
                break;
        }
        entre.close();
        fw.close();
    }
    public void ajoutRoad() throws Exception{
        System.out.println(tab.toString());
        Scanner entre = new Scanner(System.in);
        lect = new Lecteur("data.txt");
        int nb = lect.nbLine();
        String ville1,ville2,type;
        ArrayList<String> lieux = new ArrayList<String>();
        String []tab = new String[lect.nbLine()];
        System.out.println("\n\t nb de ligne"+nb);
        for(int i = 0; i < nb; i++){
            System.out.println("test");
            tab[i]= lect.getLine();
            String test[] = tab[i].split(":");
            String ville[] = test[0].split(",");
            lieux.add(ville[1]);
        }
        System.out.println("\n"+lieux.toString()+"\n");
        System.out.println("Choisiez la première lieu");
        ville1 = entre.nextLine();
        while(!lieux.contains(ville1)){
            System.out.println("Lieu inconnu");
            ville1 = entre.nextLine();
        }
        System.out.println("Choisiez la deuxième lieu");
        ville2 = entre.nextLine();
        while(!lieux.contains(ville2) && ville2!=ville1){
            System.out.println("Lieu inconnu");
            ville2 = entre.nextLine();
        }
        System.out.println("\t Menu Ajout Route\n\t1- Autoroute\n\t2- Nationale\n\t3- Départmentale");
        
        int optionmenu =entre.nextInt();
        switch(optionmenu){
            case 1:
                type= "A";
                break;
            case 2:
                type= "N";
                break;
            default:
                type= "D";
                break;
        }
        System.out.println("Entrer le distance entre les deux villes");
        int distance =entre.nextInt();
        
        String test1[] = tab[lieux.indexOf(ville1)].split(":");
        String test2[] = tab[lieux.indexOf(ville2)].split(":");
        String info1[] = tab[lieux.indexOf(ville1)].split(";;");
        String info2[] = tab[lieux.indexOf(ville2)].split(";;");
        tab[lieux.indexOf(ville1)]=info1[0]+";"+type+","+distance+"::"+test2[0]+";;";
        tab[lieux.indexOf(ville2)]=info2[0]+";"+type+","+distance+"::"+test1[0]+";;";
        
        PrintWriter writer = new PrintWriter("data.txt");
        for (int i = 0;i<tab.length;i++){
            writer.println(tab[i]);
        }
        writer.close();
        entre.close();
    }
    public void supprimerSommet() throws Exception{
        Scanner entre = new Scanner(System.in);
        ArrayList<String> lieux = new ArrayList<String>();
        lect = new Lecteur("data.txt");
        int nb = lect.nbLine();
        String []tab = new String[lect.nbLine()];
        for(int i = 0; i < nb; i++){
            tab[i]= lect.getLine();
            String test[] = tab[i].split(";");
            String ville[] = test[0].split(",");
            lieux.add(ville[1]);
        }
        System.out.println("\n"+lieux.toString()+"\n");
        System.out.println("Entrer le lieu que vous voulez supprimer");
        String lieu = entre.nextLine();
        while(!lieux.contains(lieu)){
            System.out.println("Lieu inconnu");
            lieu = entre.nextLine();
        }
        String test[] = tab[lieux.indexOf(lieu)].split(";");
        for(int i = 1;i < test.length;i++){
            String part[]= test[i].split("::");
            String route[]= part[0].split(",");
            String ville[]= part[1].split(",");
            supprimerRoad(lieu,ville[1],route[0],route[1]);
        }
        lect = new Lecteur("data.txt");
        for(int i = 0; i < nb; i++){
            tab[i]= lect.getLine();
        }
        for(int i=lieux.indexOf(lieu);i<tab.length-1;i++){
            tab[i]=tab[i+1];
        }
        
        PrintWriter writer = new PrintWriter("data.txt");
        for (int i = 0;i<tab.length-1;i++){
            writer.println(tab[i]);
        }
        writer.close();
        entre.close();
    }
    public void supprimerRoad() throws Exception{
        Scanner entre = new Scanner(System.in);
        String ville1,ville2,type;
        ArrayList<String> lieux = new ArrayList<String>();
        lect = new Lecteur("data.txt");
        int nb = lect.nbLine();
        String []tab = new String[lect.nbLine()];
        for(int i = 0; i < nb; i++){
            tab[i]= lect.getLine();
            String test[] = tab[i].split(";");
            String ville[] = test[0].split(",");
            lieux.add(ville[1]);
        }
        System.out.println("\n"+lieux.toString()+"\n");
        System.out.println("Choisiez la première lieu");
        ville1 = entre.nextLine();
        while(!lieux.contains(ville1)){
            System.out.println("Lieu inconnu");
            ville1 = entre.nextLine();
        }
        System.out.println("Choisiez la deuxième lieu");
        ville2 = entre.nextLine();
        while(!lieux.contains(ville2) &&  ville2!=ville1){
            System.out.println("Lieu inconnu");
            ville2 = entre.nextLine();
        }
        System.out.println("\t Menu Supprimer Route\n\t1- Autoroute\n\t2- Nationale\n\t3- Départmentale");
        
        int optionmenu =entre.nextInt();
        switch(optionmenu){
            case 1:
                type= "A";
                break;
            case 2:
                type= "N";
                break;
            default:
                type= "D";
                break;
        }
        System.out.println("Entrer le distance entre les deux villes");
        int distance =entre.nextInt();
        String test1[] = tab[lieux.indexOf(ville1)].split(";");
        String test2[] = tab[lieux.indexOf(ville2)].split(";");
        System.out.println(type+",5::"+test2[0]);
        boolean trouver=false;
        for(int compteur=1;compteur<test1.length && !trouver;compteur++){
            if(test1[compteur].equalsIgnoreCase(type+","+distance+"::"+test2[0])){
                trouver=true;
                for(int i =compteur;i<test1.length-1;i++){
                    test1[i]=test1[i+1];
                }
                test1[test1.length-1]="";
            }
            System.out.println(compteur);
        }
        tab[lieux.indexOf(ville1)]="";
        for(int compteur=0;compteur<test1.length;compteur++){
            tab[lieux.indexOf(ville1)]=tab[lieux.indexOf(ville1)]+test1[compteur]+";";
        }
        trouver=false;
        for(int compteur=1;compteur<test2.length && !trouver;compteur++){
            if(test2[compteur].equalsIgnoreCase(type+","+distance+"::"+test1[0])){
                trouver=true;
                for(int i =compteur;i<test2.length-1;i++){
                    test2[i]=test2[i+1];
                }
                test2[test2.length-1]="";
            }
            System.out.println(compteur);
        }
        tab[lieux.indexOf(ville2)]="";
        for(int compteur=0;compteur<test2.length;compteur++){
            tab[lieux.indexOf(ville2)]=tab[lieux.indexOf(ville2)]+test2[compteur]+";";
        }
        System.out.println(tab[lieux.indexOf(ville1)]+tab[lieux.indexOf(ville2)]);
        PrintWriter writer = new PrintWriter("data.txt");
        for (int i = 0;i<tab.length;i++){
            writer.println(tab[i]);
        }
        writer.close();
        entre.close();
    }
    public void supprimerRoad(String ville1,String ville2,String type,String distance) throws Exception{
        Scanner entre = new Scanner(System.in);
        ArrayList<String> lieux = new ArrayList<String>();
        lect = new Lecteur("data.txt");
        int nb = lect.nbLine();
        String []tab = new String[lect.nbLine()];
        for(int i = 0; i < nb; i++){
            tab[i]= lect.getLine();
            String test[] = tab[i].split(";");
            String ville[] = test[0].split(",");
            lieux.add(ville[1]);
        }
        //System.out.println("\n"+lieux.toString()+"\n");
        String test1[] = tab[lieux.indexOf(ville1)].split(";");
        String test2[] = tab[lieux.indexOf(ville2)].split(";");
        boolean trouver=false;
        for(int compteur=1;compteur<test1.length && !trouver;compteur++){
            if(test1[compteur].equalsIgnoreCase(type+","+distance+"::"+test2[0])){
                trouver=true;
                for(int i =compteur;i<test1.length-1;i++){
                    test1[i]=test1[i+1];
                }
                test1[test1.length-1]="";
            }
            //System.out.println(compteur);
        }
        tab[lieux.indexOf(ville1)]="";
        for(int compteur=0;compteur<test1.length;compteur++){
            tab[lieux.indexOf(ville1)]=tab[lieux.indexOf(ville1)]+test1[compteur]+";";
        }
        trouver=false;
        for(int compteur=1;compteur<test2.length && !trouver;compteur++){
            if(test2[compteur].equalsIgnoreCase(type+","+distance+"::"+test1[0])){
                trouver=true;
                for(int i =compteur;i<test2.length-1;i++){
                    test2[i]=test2[i+1];
                }
                test2[test2.length-1]="";
            }
            //System.out.println(compteur);
        }
        tab[lieux.indexOf(ville2)]="";
        for(int compteur=0;compteur<test2.length;compteur++){
            tab[lieux.indexOf(ville2)]=tab[lieux.indexOf(ville2)]+test2[compteur]+";";
        }
        //System.out.println(tab[lieux.indexOf(ville1)]+tab[lieux.indexOf(ville2)]);
        PrintWriter writer = new PrintWriter("data.txt");
        for (int i = 0;i<tab.length;i++){
            writer.println(tab[i]);
        }
        System.out.println("Suppression route");
        writer.close();
        entre.close();
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

