package Tests;

import DataStructure.Cell;
import DataStructure.NeighborsList;
import DataStructure.NodeType;
import DataStructure.Sommet;
import org.junit.Test;

public class NeigborsListTest {

    @Test
    public void testGetNode() {
        Sommet s1 = new Sommet("Test", NodeType.VILLE);
        Sommet s2 = new Sommet("Test2", NodeType.VILLE);
        NeighborsList nl = new NeighborsList(new Cell(s1));
        nl.add(new Cell(s2));
        assert (nl.getOrigin().getValue().getName().equals("Test"));
        assert (nl.getOrigin().getValue().getName().equals("Test2"));
    }

    @Test
    public void testGetNodeIndex() {
        Sommet s1 = new Sommet("Test", NodeType.VILLE);
        Sommet s2 = new Sommet("Test2", NodeType.VILLE);
        NeighborsList nl = new NeighborsList(new Cell(s1));
        nl.add(new Cell(s2));
        assert (nl.getOrigin().getValue().getName().equals("Test"));
        assert (nl.getOrigin().getValue().getName().equals("Test2"));
    }

    @Test
    public void testGetNodeByName() {
        Sommet s1 = new Sommet("Test", NodeType.VILLE);
        Sommet s2 = new Sommet("Test2", NodeType.VILLE);
        NeighborsList nl = new NeighborsList(new Cell(s1));
        nl.add(new Cell(s2));
        assert (nl.getOrigin().getValue().getName().equals("Test"));
        assert (nl.getOrigin().getValue().getName().equals("Test2"));
    }

    @Test
    public void testAdd() {
        Sommet s1 = new Sommet("Test", NodeType.VILLE);
        Sommet s2 = new Sommet("Test2", NodeType.VILLE);
        NeighborsList nl = new NeighborsList(new Cell(s1));
        nl.add(new Cell(s2));
        assert (nl.getOrigin().getValue().getName().equals("Test"));
        assert (nl.getOrigin().getValue().getName().equals("Test2"));
    }
}
