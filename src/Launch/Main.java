package Launch;

import DataStructure.*;
import IHM.*;
import ProcessingEngine.*;
import com.formdev.flatlaf.*;


import javax.swing.*;
import java.io.*;

import static com.formdev.flatlaf.FlatLaf.updateUI;


public class Main {

    Lecteur lect;
    NodeList tab = new NodeList();
    FrameManager frame;
    JFrame dataChoiceFrame;
    double linkFactor = 1.5;
    Settings settings;
    
    Main() throws Exception {
        FlatDarkLaf.install();
        settings = getSerializedSettings();
        init();
    }
    public static void main(String []args) throws Exception {
        new Main();
    }
    public Settings getSerializedSettings() throws Exception{
        // Check if te file setting exist
        File f = new File("config\\settings.ser");
        if(!f.exists()){
            JOptionPane.showMessageDialog(null, "Le fichier de configuration n'existe pas." +
                    " Création de celui-ci\n");
            //ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(new Settings());

        }
        // Check if the file is empty
        else if(f.length() == 0){
            JOptionPane.showMessageDialog(null, "Le fichier de configuration semble corrompu." +
                    " Re-création de celui-ci\n");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(new Settings());
        }
        return (Settings) new ObjectInputStream(new FileInputStream(f)).readObject();
    }
    public void init() throws Exception {

        remplir();

        preTraiter();
        PathFinder pf = new PathFinder(tab);

        frame = new FrameManager(tab, settings.getWidth(), settings.getHeight(), pf, this);
        applySettings();
    }

    public boolean preTraiter(){
        int nb = JOptionPane.showConfirmDialog(null, "Voulez-vous pré-traiter les données ?",
                "Pré-traiter", JOptionPane.YES_NO_OPTION);
        if(nb == JOptionPane.YES_OPTION){
            PathBuilder pb = new PathBuilder(tab, linkFactor);
            pb.buildEveryLinks();
            pb.syncronize();
            pb.makeRelate();

            setTab(pb.getTab());
            pb.syncronize();
        }
        findEquivalence();
        return nb == JOptionPane.YES_OPTION;
    }
    public void serializeSettings(){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("config\\settings.ser"));
            oos.writeObject(settings);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void afficherSommets(){
        for(int i = 0; i < this.tab.size(); i++){
            System.out.print(this.tab.getNode(i).getName() + " : ");
            System.out.print(this.tab.getNode(i).getStringType());

            for(Cell cl : tab.get(i).getNeighbors()){

                System.out.print(" -> " + cl.getValue().getName());

                System.out.print("(" + cl.getRoute().getTypeStr() + " de "
                    + cl.getRoute().getKm() + "km)");

            }
            System.out.println("");
        }
    }


    public void remplir() throws Exception {
        lect = new Lecteur("src\\data.csv");
        int nb = lect.nbLine();
        for(int i = 0; i < nb; i++){
            String line = lect.getLine();
            String part1 = line.split(":[0-9]|;|:")[0];
            String part2 = line.split(":;")[1].replace(" ", "");

            //String type = part1.split(",[a-zA-Z]")[0].replace(" ", "");
            String type = part1.split(",")[0].replace(" ", "");
            String nom = part1.split(",")[1];
            Double[] pos = {Double.valueOf(part1.split((","))[2]), Double.valueOf(part1.split(",")[3])};

            NodeType typeS = findTypeSm(type);
            Sommet sm = new Sommet(nom, typeS, pos[0], pos[1]);
            Cell linkTab[] = readLink(part2);

            this.tab.add(new NeighborsList(new Cell(sm)));

            for(int j = 0; j < linkTab.length; j++){
                this.tab.get(tab.size()-1).add(linkTab[j]);
            }
        }
    }

    public NodeType findTypeSm(String str){
        NodeType typeS;
        str = str.replace(" ", "");
        switch(str){
            case "R":
                typeS = NodeType.RESTAURANT;
                break;
            case "L":
                typeS = NodeType.LOISIR;
                break;
            case "S":
                typeS = NodeType.SERVICE;
                break;
            case "V":
                typeS = NodeType.VILLE;
                break;
            default:
                throw new IllegalArgumentException("Type de sommet non reconnu"+ str);
        }
        return typeS;
    }
    public RoadType findTypeRoad(String str){
        RoadType typeS;
        switch(str){
            case "D":
                typeS = RoadType.DEPARTMENTALE;
                break;
            case "N":
                typeS = RoadType.NATIONALE;
                break;
            case "A":
                typeS = RoadType.AUTOROUTE;
                break;
            default:
                throw new IllegalArgumentException("Type de route non reconnu " + str + ".");
        }
        return typeS;
    }

    public Cell[] readLink(String part2){

        int nbLink = part2.split("[a-zA-Z];").length-1;
        Cell linkTabCl[] = new Cell[nbLink];
        for(int i = 0; i < nbLink; i++){
            String linkStr = part2.split(";")[i];
            
            String strSm = linkStr.split("::")[1];

            String allRoad = linkStr.split("::")[0];;
            double km = Double.parseDouble(allRoad.split(",")[1]);
            RoadType type = findTypeRoad(allRoad.split(",")[0]);
            strSm = strSm.replace(";", "");
            Sommet linkSm = new Sommet(strSm.split(",")[1], findTypeSm(strSm.split(",")[0]));
            Road route = new Road(type, km);
            linkTabCl[i] = new Cell(linkSm, route);

        }
        return linkTabCl;
    }

    public void findEquivalence(){
        for(int i = 0; i < this.tab.size(); i++){
            for(int j = 0; j < this.tab.get(i).size(); j++){
                for(int k = 0; k < this.tab.size(); k++){
                    if (tab.getNode(k).getName().replace(" ", "").equals(
                            tab.get(i).get(j).getValue().getName().replace(" ", "")) &&
                            tab.getNode(k).getType() == tab.get(i).get(j).getValue().getType()){
                        tab.get(i).get(j).setValue(tab.getNode(k));
                    }
                }
            }
        }
    }
    public NodeList findEquivalence(NodeList list){
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < list.get(i).size(); j++){
                for(int k = 0; k < list.size(); k++){
                    if (list.getNode(k).getName().replace(" ", "").equals(
                            list.get(i).get(j).getValue().getName().replace(" ", "")) &&
                            list.getNode(k).getType() == list.get(i).get(j).getValue().getType()){
                        list.get(i).get(j).setValue(list.getNode(k));
                    }
                }
            }
        }
        return list;
    }
    public void removeRoads(){
        for(NeighborsList list : this.tab){
            list.removeAll(list.getNeighbors());
        }
        frame.reinit();
    }
    public void removeNode(Sommet sm){
        tab.remove(tab.getNodeIndex(sm));
        for(NeighborsList nblist : tab){
            for(int i = nblist.size()-1; i >= 0; i--){
                if(nblist.get(i).getValue().equals(sm)){
                    nblist.remove(i);
                }
            }
        }
        frame.reinit();
    }

    public void reinit() throws Exception {
        tab.clear();
        remplir();
        if(preTraiter()){
            rebuild(tab);
        }
        frame.setTab(tab);
        frame.reinit();

    }
    public void rebuild(NodeList nodeList){
        nodeList = findEquivalence(nodeList);
        PathBuilder pb = new PathBuilder(nodeList, linkFactor);
        pb.buildEveryLinks();
        pb.syncronize();
        pb.makeRelate();

        setTab(pb.getTab());
        pb.syncronize();
        frame.reinit();
    }

    public void addSommet(Sommet sm){
        tab.add(new NeighborsList(new Cell(sm)));
        frame.reinit();
    }
    public void applyNewList(NodeList nl){
        tab = nl;
        frame.reinit();
    }
    public void changeSettings(int width, int height, boolean fullScreen, double factor){
        if(width > 600){
            settings.setWidth(width);
        }
        if(height > 600){
            settings.setHeight(height);
        }
        settings.setFullScreen(fullScreen);
        if(factor >= 1){
            settings.setLinkFactor(factor);
        }
        applySettings();
    }
    public void applySettings(){

        if(settings.isDarkMod())
            FlatDarkLaf.install();
        else
            FlatLightLaf.install();
        if(settings.getWidth() > 600 && settings.getHeight() > 600) {
            frame.setSize(settings.getWidth(), settings.getHeight());
        }
        if(settings.getLinkFactor() > 1)
            linkFactor = settings.getLinkFactor();
        if(settings.isFullscreen())
            frame.setFullScreen();
        else
            frame.setWindowed();

        linkFactor = settings.getLinkFactor();

        updateUI();

    }


    public NodeList getTab(){
        return this.tab;
    }
    public void setTab(NodeList tab){
        this.tab = tab;
    }
    public double getLinkFactor(){
        return this.linkFactor;
    }
    public Settings getSettings(){
        return this.settings;
    }

    public void applyDarkMode(boolean b) {
        settings.setDarkMod(b);
        try {
            if(settings.isDarkMod()){
                FlatDarkLaf.install();
            }
            else{
                FlatLightLaf.install();
            }
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

