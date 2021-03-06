package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.model.Node;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.SneakyThrows;

public class Graph {

  private static Graph graph = null;
  public ArrayList<Node> listOfNodes = new ArrayList<>();
  public Hashtable<Node, ArrayList<Node>> map;
  public Hashtable<String, Node> key;

  /** NEVER CALL THIS EVER. this function is NOT what you want */
  private Graph() {
    ArrayList<Node> listOfNeighbors = new ArrayList<>();
    map = new Hashtable<Node, ArrayList<Node>>();
    key = new Hashtable<String, Node>();

    for (Node n : getInitialNodes()) {
      listOfNeighbors = getInitialNeighbors(n);
      map.put(n, listOfNeighbors);
      key.put(n.getID(), n);
      listOfNodes.add(n);
    }
  }

  public static Graph getInstance() {
    if (graph == null) {
      graph = new Graph();
    }

    return graph;
  }

  public static void resetAll() {
    graph = new Graph();
  }

  @SneakyThrows
  private ArrayList<Node> getInitialNeighbors(Node node) {
    ArrayList<Node> listOfNeighbors = new ArrayList<>();

    PreparedStatement pstmt = null;
    pstmt =
        DatabaseConnection.getConnection()
            .prepareStatement("SELECT * FROM Edges WHERE startNode = ? OR endNode = ?");
    pstmt.setString(1, node.getID());
    pstmt.setString(2, node.getID());
    ResultSet rset = pstmt.executeQuery();

    while (rset.next()) {
      String startNode = rset.getString("startNode");
      String endNode = rset.getString("endNode");

      Node start = ArchiveNE.getNode(startNode);
      Node end = ArchiveNE.getNode(endNode);

      if (startNode.equals(node.getID())) {
        if (!listOfNeighbors.contains(end)) {
          listOfNeighbors.add(end);
        }

      } else if (endNode.equals(node.getID())) {
        if (!listOfNeighbors.contains(start)) {
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
  @SneakyThrows
  private ObservableList<Node> getInitialNodes() {
    ObservableList<Node> nodeList = FXCollections.observableArrayList();

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
      Node n = new Node(xcoord, ycoord, floor, building, nodeType, longName, team);
      nodeList.add(n);
    }
    // must close to get proper info from db
    rset.close();
    pstmt.close();

    return nodeList;
  }

  public void addNode(Node node) {

    boolean IDAlreadyExists = true;
    while (IDAlreadyExists) {
      String ID = node.getID();
      IDAlreadyExists = key.get(ID).getID().equals(ID);
      node.setID(node.randIDGen());
    }
    map.put(node, new ArrayList<>());
    key.put(node.getID(), node);
  }

  /**
   * Adds an edge with automatic bidirectioinality. No need to call this function twice to create an
   * edge.
   *
   * @param s, the start node
   * @param e, the end node
   */
  public void addEdge(String s, String e) {
    Node start = key.get(s);
    Node end = key.get(e);

    map.get(end).add(start);
    map.get(start).add(end);
  }

  public void deleteNode(String nodeID) {
    Node n = key.get(nodeID);

    key.remove(nodeID);
    map.remove(n);
  }

  public void deleteAllEdges(String s) {

    Node node = key.get(s);

    ArrayList<Node> neighbors = map.get(node);

    neighbors.forEach(
        e -> {
          map.get(e).remove(node);
        });

    map.remove(node);
    map.put(node, new ArrayList<>());
  }

  public void deleteEdge(String sn, String en) {
    Node startNode = key.get(sn);
    Node endNode = key.get(en);

    map.get(startNode).remove(endNode);
    map.get(endNode).remove(startNode);
  }

  public Node getNode(String nodeID) {
    return key.get(nodeID);
  }

  /**
   * Edit the node inside of the key hashtable
   *
   * @param pastNode, the node you want to edit
   * @param futureNode, the values you desire for the new node, stored in a convenient manner
   */
  public void editNode(Node pastNode, Node futureNode) {

    String nodeID = pastNode.getID();

    // get the node from our hashtable that we want to make edits to
    Node editingNode = key.get(nodeID);

    editingNode.setXCoord(futureNode.getXCoord());
    editingNode.setYCoord(futureNode.getYCoord());
    editingNode.setFloor(futureNode.getFloor());
    editingNode.setLongName(futureNode.getLongName());
    editingNode.setBuilding(futureNode.getBuilding());
    editingNode.setNodeType(futureNode.getNodeType());
  }

  public double getEdgeWeight(Node start, Node end){

    if(start.getNodeType().equals("ELEV") && end.getNodeType().equals("ELEV")){
      return 1;
    }else if(start.getNodeType().equals("STAI") && end.getNodeType().equals("STAI")){
      return 2;
    }else {
      double X1 = start.getXCoord();
      double Y1 = start.getXCoord();
      double X2 = start.getXCoord();
      double Y2 = start.getXCoord();
      return Math.sqrt(Math.pow((Y2-Y1),2) + Math.pow((X2-X1),2));
    }
  }
}
