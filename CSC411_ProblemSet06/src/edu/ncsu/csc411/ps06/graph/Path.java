package edu.ncsu.csc411.ps06.graph;

import java.util.ArrayList;

/**
 * Represents a single Path between two vertices in a Bayesian Network. 
 * DO NOT MODIFY.

 * @author Adam Gaweda
 */
@SuppressWarnings("rawtypes")
public class Path {
  private ArrayList<Vertex> vertices;

  /**
   * Instantiates the Vertex Array List for this path
   */
  public Path() {
    this.vertices = new ArrayList<Vertex>();
  }

  /**
   * Adds a vertex to the path.
   * @param vertex - the Vertex
   */
  public void addVertex(Vertex vertex) {
    this.vertices.add(vertex);
  }

  /** 
   * Returns the ArrayList of Vertices.
   * @return an ArrayList of vertices
   */
  public ArrayList<Vertex> getVertices() {
    return this.vertices;
  }

  /**
   * Returns the first Vertex in the Path.
   * @return the first Vertex in the Path
   */
  public Vertex getStart() {
    return this.vertices.get(0);
  }

  /**
   * Returns the last Vertex in the Path.
   * @return the last Vertex in the Path
   */
  public Vertex getEnd() {
    return this.vertices.get(this.vertices.size() - 1);
  }
}
