

class Road{

    enum RoadType{
        AUTOROUTE,
        NATIONALE,
        DEPARTMENTALE
    }
    private RoadType type; // 0 : autoroute, 1 : departementale, 2 : regionnale, 3 : nationnale
    private double km;

    public Road(){
        this.type = RoadType.AUTOROUTE;
        this.km = 0;
    }

    public Road(RoadType type, double km) {
        this.type = type;
        this.km = km;
    }
    public static RoadType detectType(Sommet sommet, Sommet sommet1) {
        RoadType type;
        double distance = DistanceGetter.distance(sommet, sommet1);
        if(distance < 3){
            type = RoadType.DEPARTMENTALE;
        }
        else if(distance < 10){
            type = RoadType.NATIONALE;
        }
        else {
            type = RoadType.AUTOROUTE;
        }
        return type;
    }

    public RoadType getType() {
        return this.type;
    }
    public String getTypeStr() {
        String typeStr;
        switch(this.type){
            case DEPARTMENTALE:
                typeStr = "departementale";
                break;
            case NATIONALE:
                typeStr = "nationnale";
                break;
            default:
                typeStr = "autoroute";
                break;
        }
        return typeStr;
    }

    public char getCharType() {
        char typeStr;
        switch(this.type){
            case DEPARTMENTALE:
                typeStr = 'D';
                break;
            case NATIONALE:
                typeStr = 'N';
                break;
            default:
                typeStr = 'A';
                break;
        }
        return typeStr;
    }

    public void setType(RoadType type) {
        this.type = type;
    }

    public double getKm() {
        return this.km;
    }

    public void setKm(int km) {
        this.km = km;
    }

}