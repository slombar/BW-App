package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.model.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.LinkedList;

public class Graph {

    private static Graph graph = null;

    public LinkedList<Node> listOfNodes = new LinkedList<>();
    public Hashtable<Node, LinkedList<Node>> map;
    public Hashtable<String, Node> key;

    private Graph() {
        LinkedList<Node> listOfNeighbors;
        for (Node n: getInitialNodes()) {
            listOfNeighbors = getInitialNeighbors(n);
            map.put(n, listOfNeighbors);
            key.put(n.getID(), n);
            listOfNodes.add(n);
        }
    }

    public static Graph getInstance(){

        if (graph == null)
            graph = new Graph();

        return graph;
    }

    @SneakyThrows
    private LinkedList<Node> getInitialNeighbors(Node node){
        LinkedList<Node> listOfNeighbors = new LinkedList<>();

        PreparedStatement pstmt = null;
        pstmt =
                DatabaseConnection.getConnection()
                        .prepareStatement("SELECT * FROM Edges WHERE startNode = ? OR endNode = ?");
        pstmt.setString(1,node.getID());
        pstmt.setString(2,node.getID());
        ResultSet rset = pstmt.executeQuery();

        while (rset.next()) {
            String startNode = rset.getString("startNode");
            String endNode = rset.getString("endNode");

            Node start = ArchiveNE.getNode(startNode);
            Node end = ArchiveNE.getNode(endNode);

                if (startNode.equals(node.getID())) {
                    if(!listOfNeighbors.contains(end)) {
                        listOfNeighbors.add(end);
                    }
                } else if (endNode.equals(node.getID())) {
                    if(!listOfNeighbors.contains(start)) {
                        listOfNeighbors.add(start);
                    }
                } else {
                    System.out.println("BAD BAD NONONONONONON");
                }
        }

        rset.close();
        pstmt.close();

        return listOfNeighbors;
    }

    /**
     * Displays all the nodes from the database
     *
     * @return ObservableList<Node> type, filled with nodes
     */
    private ObservableList<Node> getInitialNodes() {
        ObservableList<Node> nodeList = FXCollections.observableArrayList();

        try {
            // statments to grab all node values from db
            PreparedStatement pstmt = null;
            pstmt = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM Nodes");
            // returns results from DB
            ResultSet rset = pstmt.executeQuery();

            // temp variables
            String ID = "";
            int xcoord = 0;
            int ycoord = 0;
            String floor = "";
            String building = "";
            String nodeType = "";
            String longName = "";
            String shortName = "";
            String team = "";
            boolean visible = false;


            while (rset.next()) {
                // add data from result set of query to observable list for processing
                ID = rset.getString("NODEID");
                xcoord = rset.getInt("XCOORD");
                ycoord = rset.getInt("YCOORD");
                floor = rset.getString("FLOOR");
                building = rset.getString("BUILDING");
                nodeType = rset.getString("NODETYPE");
                longName = rset.getString("LONGNAME");
                shortName = rset.getString("SHORTNAME");
                team = rset.getString("TEAMASSIGNED");
                visible = rset.getBoolean("VISIBLE");
                // add to observable list
                Node n =
                        new Node(xcoord, ycoord, floor, building, nodeType, longName, team);
                nodeList.add(n);
            }
            // must close to get proper info from db
            rset.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nodeList;
    }

    public void addNode(Node node){
        map.put(node, new LinkedList<Node>());
        key.put(node.getID(), node);
    }

    public void addNewEdge(Node start, Node end){
        map.get(start).add(end);
        map.get(end).add(start);
    }

    public void deleteAllEdges(Node node){

        LinkedList<Node> neighbors = map.get(node);

        neighbors.forEach(
                e ->{
                    map.get(e).remove(node);
                });

        map.remove(node);
        map.put(node, new LinkedList<Node>());
    }

    public void deleteEdge(Node startNode, Node endNode){
        map.get(startNode).remove(endNode);
        map.get(endNode).remove(startNode);
    }

    public Node getNode(String nodeID){
       return key.get(nodeID);
    }



}
