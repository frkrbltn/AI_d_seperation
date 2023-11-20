package edu.ncsu.csc411.ps06.dseperation;

import java.util.ArrayList;

import edu.ncsu.csc411.ps06.graph.Graph;
import edu.ncsu.csc411.ps06.graph.Path;
import edu.ncsu.csc411.ps06.graph.Vertex;
import edu.ncsu.csc411.ps06.util.EdgeType;
// import Edge java file
import edu.ncsu.csc411.ps06.graph.Edge;

/**
 * Problem Set 06 - For this Problem Set you will be implementing the D-Separation
 * algorithm discussed in class. The dSeparation method below provides you with paths
 * between two vertices as an ArrayList and the observed evidence, also as an ArrayList.
 * Given the paths and evidence, your task is to implement D-Separation and return either
 * TRUE or FALSE, indicating whether the first vertex and last vertices in the paths are
 * independent from each other. Recall, for two nodes to be independent, all  paths need
 * to be INACTIVE (return TRUE). An ACTIVE path (return FALSE) means that the nodes may
 * have influence over each other.
 * 
 * For this problem set, you will need to convert the paths into a set of triples and evaluate
 * each triple based on the rules provided in class. Utilize the graph's getEdge method to get
 * vertices' edge type (TO or FROM). As in, if graph.getEdge(A, B) returned an Edge that has
 * EdgeType.TO, then the edge can be read as A TO B, or A -> B. If it instead return EdgeType.FROM,
 * then the edge can be read as A FROM B, or A <- B.
 * @param <V> - the Vertex datatype
 */
public class DSeparation<V extends Comparable<V>> {
  public Graph<V> graph;

  /** Assigns the graph to this object.
   * @param graph - the graph
   */
  public DSeparation(Graph<V> graph) {
    this.graph = graph;
  }

  /**
   * Replace this docstring comment with an explanation of your implementation.
   */
  public boolean dSeparation(ArrayList<Path> paths, ArrayList<Vertex<V>> evidence) {
    // Feel free to utilize the Graph object's getEdge and getEdgeType for this PS.
    // Create a helper method that returns a triple
    // given a path and the graph.

    // Iterate thorough path
    for (Path path : paths) {
      // Iterate through each vertex in the path
      if (!independent(path, evidence)) {
        return false;
      }
    }
    return true;
  }

  private boolean independent(Path path, ArrayList<Vertex<V>> evidence) {
    ArrayList<Vertex> vertices = path.getVertices();
    for (int i = 0; i < vertices.size() - 2; i++) {
        Vertex<V> A = vertices.get(i);
        Vertex<V> B = vertices.get(i + 1);
        Vertex<V> C = vertices.get(i + 2);
        if (!active(A, B, C, evidence)) {
            return false; // If any triple is active, the path is active
        }
    }
    return true; // If no triples are active, the path is inactive
  }

  private boolean active(Vertex<V> A, Vertex<V> B, Vertex<V> C, ArrayList<Vertex<V>> evidence) {
    EdgeType edgeAB = graph.getEdge(A, B).getEdgeType();
    EdgeType edgeBC = graph.getEdge(B, C).getEdgeType();

    if (edgeAB == EdgeType.TO && edgeBC == EdgeType.TO) {
        // Chain A -> B -> C
        return evidence.contains(B);
    } else if (edgeAB == EdgeType.FROM && edgeBC == EdgeType.FROM) {
        // Fork A <- B -> C
        return evidence.contains(B);
    } else if ((edgeAB == EdgeType.TO && edgeBC == EdgeType.FROM) || (edgeAB == EdgeType.FROM && edgeBC == EdgeType.TO)) {
        // Collider A -> B <- C or A <- B -> C
        return inactive(B, evidence);
    }
    return false;
}

private boolean inactive(Vertex<V> collider, ArrayList<Vertex<V>> evidence) {
    // Collider is inactive if neither the collider nor its descendants are in evidence
    if (evidence.contains(collider)) return false;

    for (Vertex<V> descendent : collider.neighbors) {
        if (evidence.contains(descendent)) return false;
    }

    return true;
}

}
