import javax.swing.*;


public class Main {

    Lecteur lect;
    NodeList tab = new NodeList();
    FrameManager frame;
    JFrame dataChoiceFrame;
    
    Main() throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        init();
    }
    public static void main(String []args) throws Exception {
        new Main();

    }
    public void init() throws Exception {

        remplir();

        // JOptionPane for init or not
        preTraiter();
        PathFinder pf = new PathFinder(tab);

        frame = new FrameManager(tab, 1920, 1080, pf, this);


    }

    public boolean preTraiter(){
        int nb = JOptionPane.showConfirmDialog(null, "Voulez-vous pré-traiter les données ?",
                "Pré-traiter", JOptionPane.YES_NO_OPTION);
        if(nb == JOptionPane.YES_OPTION){
            PathBuilder pb = new PathBuilder(tab);
            pb.buildEveryLinks();
            pb.syncronize();
            pb.makeRelate();

            setTab(pb.getTab());
            pb.syncronize();
        }
        findEquivalence();
        return nb == JOptionPane.YES_OPTION;
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
            //line = line.replace(" ", "");
            String part1 = line.split(":[0-9]|;|:")[0]; // ici pour la localisation
            String part2 = line.split(":;")[1].replace(" ", "");

            String type = part1.split(",[A-Z]")[0].replace(" ", "");
            String nom = part1.split(",")[1];
            Double[] pos = {Double.valueOf(part1.split((","))[2]), Double.valueOf(part1.split(",")[3])};


            NodeList.NodeType typeS = findTypeSm(type);
            Sommet sm = new Sommet(nom, typeS, pos[0], pos[1]);
            Cell linkTab[] = readLink(part2);

            this.tab.add(new NeighborsList(new Cell(sm)));

            for(int j = 0; j < linkTab.length; j++){
                this.tab.get(tab.size()-1).add(linkTab[j]);
            }
        }
    }

    public NodeList.NodeType findTypeSm(String str){
        NodeList.NodeType typeS;
        switch(str){
            case "R":
                typeS = NodeList.NodeType.RESTAURANT;
                break;
            case "L":
                typeS = NodeList.NodeType.LOISIR;
                break;
            case "S":
                typeS = NodeList.NodeType.SERVICE;
                break;
            default:
                typeS = NodeList.NodeType.VILLE;
                break;
        }
        return typeS;
    }
    public Road.RoadType findTypeRoad(String str){
        Road.RoadType typeS;
        switch(str){
            case "D":
                typeS = Road.RoadType.DEPARTMENTALE;
                break;
            case "N":
                typeS = Road.RoadType.NATIONALE;
                break;
            default:
                typeS = Road.RoadType.AUTOROUTE;
                break;
        }
        return typeS;
    }

    public Cell[] readLink(String part2){

        int nbLink = part2.split("[a-zA-Z];").length-1;
        System.out.println(nbLink);
        Cell linkTabCl[] = new Cell[nbLink];
        for(int i = 0; i < nbLink; i++){
            String linkStr = part2.split(";[A-Z]")[i];
            
            String strSm = linkStr.split("::")[1];

            String allRoad = linkStr.split("::")[0];;
            int km = Integer.parseInt(allRoad.split(",")[1]);
            Road.RoadType type = findTypeRoad(allRoad.split(",")[0]);
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
                    if (tab.getNode(k).getName().equals(tab.get(i).get(j).getValue().getName()) &&
                            tab.getNode(k).getType() == tab.get(i).get(j).getValue().getType()){
                        tab.get(i).get(j).setValue(tab.getNode(k));
                    }
                }
            }
        }
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
        findEquivalence();
        frame.setTab(tab);
        frame.reinit();

    }
    public void rebuild(NodeList nodeList){
        PathBuilder pb = new PathBuilder(nodeList);
        pb.buildEveryLinks();
        pb.syncronize();
        pb.makeRelate();

        setTab(pb.getTab());
        pb.syncronize();
        findEquivalence();
    }

    public void addSommet(Sommet sm){
        tab.add(new NeighborsList(new Cell(sm)));
        frame.reinit();
    }
    public void applyNewList(NodeList nl){
        tab = nl;
        frame.reinit();
    }


    public NodeList getTab(){
        return this.tab;
    }
    public void setTab(NodeList tab){
        this.tab = tab;
    }
}

