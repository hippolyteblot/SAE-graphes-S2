

class Road{

    private int type; // 0 : autoroute, 1 : departementale, 2 : regionnale, 3 : nationnale
    private int km;

    public Road(){
        this.type = 0;
        this.km = 0;
    }

    public Road(int type, int km) {
        this.type = type;
        this.km = km;
    }

    public int getType() {
        return this.type;
    }
    public String getTypeStr() {
        String typeStr;
        switch(this.type){
            case 1:
                typeStr = "departementale";
                break;
            case 2:
                typeStr = "regionnale";
                break;
            case 3:
                typeStr = "regionnale";
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
            case 1:
                typeStr = 'D';
                break;
            case 2:
                typeStr = 'R';
                break;
            case 3:
                typeStr = 'N';
                break;
            default:
                typeStr = 'A';
                break;
        }
        return typeStr;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getKm() {
        return this.km;
    }

    public void setKm(int km) {
        this.km = km;
    }

}