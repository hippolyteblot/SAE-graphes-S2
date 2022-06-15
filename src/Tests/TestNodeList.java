package Tests;

import DataStructure.*;
import org.junit.Test;

public class TestNodeList {

    @Test
    public void testGetNode() {
        Sommet s1 = new Sommet("Test", NodeType.VILLE);
        Sommet s2 = new Sommet("Test2", NodeType.VILLE);
        NodeList nl = new NodeList(new Cell(s1));
        nl.add(new NeighborsList(new Cell(s2)));
        assert(nl.getNode(0).getName().equals("Test"));
        assert(nl.getNode(1).getName().equals("Test2"));
    }

    @Test
    public void testGetNodeIndex() {
        Sommet s1 = new Sommet("Test", NodeType.VILLE);
        Sommet s2 = new Sommet("Test2", NodeType.VILLE);
        NodeList nl = new NodeList(new Cell(s1));
        nl.add(new NeighborsList(new Cell(s2)));
        assert(nl.getNodeIndex(s1) == 0);
        assert(nl.getNodeIndex(s2) == 1);
    }

    @Test
    public void testGetNodeByName() {
        Sommet s1 = new Sommet("Test", NodeType.VILLE);
        Sommet s2 = new Sommet("Test2", NodeType.VILLE);
        NodeList nl = new NodeList(new Cell(s1));
        nl.add(new NeighborsList(new Cell(s2)));
        assert(nl.getNodeByName("Test").getName().equals("Test"));
        assert(nl.getNodeByName("Test2").getName().equals("Test2"));
    }

    @Test
    public void testAdd() {
        Sommet s1 = new Sommet("Test", NodeType.VILLE);
        Sommet s2 = new Sommet("Test2", NodeType.VILLE);
        NodeList nl = new NodeList(new Cell(s1));
        nl.add(new NeighborsList(new Cell(s2)));
        assert(nl.getNode(0).getName().equals("Test"));
        assert(nl.getNode(1).getName().equals("Test2"));
    }

    @Test
    public void testGetAllNodes() {
        Sommet s1 = new Sommet("Test", NodeType.VILLE);
        Sommet s2 = new Sommet("Test2", NodeType.VILLE);
        NodeList nl = new NodeList(new Cell(s1));
        nl.add(new NeighborsList(new Cell(s2)));
        assert(nl.getAllNodes().size() == 2);
    }

    @Test
    public void testGetAllNodesOfType() {
        Sommet s1 = new Sommet("Test", NodeType.VILLE);
        Sommet s2 = new Sommet("Test2", NodeType.VILLE);
        NodeList nl = new NodeList(new Cell(s1));
        nl.add(new NeighborsList(new Cell(s2)));
        assert(nl.getAllNodesOfType(NodeType.VILLE).size() == 2);
    }

    @Test
    public void testRemoveListByOrigin() {
        Sommet s1 = new Sommet("Test", NodeType.VILLE);
        Sommet s2 = new Sommet("Test2", NodeType.VILLE);
        NodeList nl = new NodeList(new Cell(s1));
        nl.add(new NeighborsList(new Cell(s2)));
        nl.removeListByOrigin(s1);
        assert(nl.getNode(0).getName().equals("Test2"));
    }
}
