
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Updater {
    NodeList tab;

    Updater(NodeList tab){
        this.tab = tab;
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
                this.tab.add(new NodeList(new Cell(sm)));
                fw.write("\nR,"+lieu+":;;");
                break;
            case 3:
                System.out.println("Entrer le nom du lieu de loisir");
                entre.nextLine();
                lieu = entre.nextLine();
                sm = new Sommet(lieu,2 );
                System.out.println("L,"+lieu);
                this.tab.add(new NodeList(new Cell(sm)));
                fw.write("\nL,"+lieu+":;;");
                break;
            default:
                System.out.println("Entrer le nom de la ville");
                entre.nextLine();
                lieu = entre.nextLine();
                sm = new Sommet(lieu,0 );
                System.out.println("V,"+lieu);
                this.tab.add(new NodeList(new Cell(sm)));
                fw.write("\nR,"+lieu+":;;");
                break;
        }
        entre.close();
        fw.close();
    }
    public void ajoutRoad() throws Exception{
        Lecteur lect = new Lecteur("data.txt");
        System.out.println(tab.toString());
        Scanner entre = new Scanner(System.in);
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
        Lecteur lect = new Lecteur("data.txt");
        Scanner entre = new Scanner(System.in);
        ArrayList<String> lieux = new ArrayList<String>();
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
        Lecteur lect = new Lecteur("data.txt");
        Scanner entre = new Scanner(System.in);
        String ville1,ville2,type;
        ArrayList<String> lieux = new ArrayList<String>();
        
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
        Lecteur lect = new Lecteur("data.txt");
        int nb = lect.nbLine();
        String []tab = new String[lect.nbLine()];
        for(int i = 0; i < nb; i++){
            tab[i]= lect.getLine();
            String test[] = tab[i].split(";");
            String ville[] = test[0].split(",");
            lieux.add(ville[1]);
        }
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
        }
        tab[lieux.indexOf(ville2)]="";
        for(int compteur=0;compteur<test2.length;compteur++){
            tab[lieux.indexOf(ville2)]=tab[lieux.indexOf(ville2)]+test2[compteur]+";";
        }
        PrintWriter writer = new PrintWriter("data.txt");
        for (int i = 0;i<tab.length;i++){
            writer.println(tab[i]);
        }
        System.out.println("Suppression route");
        writer.close();
        entre.close();
    } 
}
