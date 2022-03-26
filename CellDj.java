public class CellDj {
    
    Sommet sm;
    int distance;
    Sommet sm2;

    public CellDj() {
    }

    public CellDj(Sommet sm, int distance, Sommet sm2) {
        this.sm = sm;
        this.distance = distance;
        this.sm2 = sm2;
    }

    public Sommet getSm() {
        return this.sm;
    }

    public void setSm(Sommet sm) {
        this.sm = sm;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Sommet getSm2() {
        return this.sm2;
    }

    public void setSm2(Sommet sm2) {
        this.sm2 = sm2;
    }

    public CellDj sm(Sommet sm) {
        setSm(sm);
        return this;
    }

    public CellDj distance(int distance) {
        setDistance(distance);
        return this;
    }

    public CellDj sm2(Sommet sm2) {
        setSm2(sm2);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " sm='" + getSm() + "'" +
            ", distance='" + getDistance() + "'" +
            ", sm2='" + getSm2() + "'" +
            "}";
    }

}
