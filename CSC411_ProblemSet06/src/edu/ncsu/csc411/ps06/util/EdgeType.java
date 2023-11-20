package edu.ncsu.csc411.ps06.util;

/**
 * Simple enumeration to represent the DAG structure of the
 * Bayesian Network.
 * DO NOT MODIFY.

 * @author Adam Gaweda
 */
public enum EdgeType {
  /** A -> B, A to B */
  TO,
  /** A <- B, B from A */
  FROM;
}
