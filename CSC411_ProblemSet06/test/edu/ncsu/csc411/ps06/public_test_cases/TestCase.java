package edu.ncsu.csc411.ps06.public_test_cases;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc411.ps06.dseperation.DSeparation;
import edu.ncsu.csc411.ps06.graph.Edge;
import edu.ncsu.csc411.ps06.graph.Graph;
import edu.ncsu.csc411.ps06.graph.Path;
import edu.ncsu.csc411.ps06.graph.Vertex;
import edu.ncsu.csc411.ps06.util.EdgeType;

class TestCase {
  /** Graph inspired by https://examples.bayesserver.com/d-separation . */
  Graph<String> graph;
  Vertex<String> VtA;
  Vertex<String> hTB;
  Vertex<String> TBoC;
  Vertex<String> S;
  Vertex<String> hLC;
  Vertex<String> hB;
  Vertex<String> XR;
  Vertex<String> D;

  @BeforeEach
  public void setUp() {
    graph = new Graph<String>();

    VtA = new Vertex<String>("Visit To Asia");
    hTB = new Vertex<String>("Has Tuberculosis");
    TBoC = new Vertex<String>("Tuberculosis Or Cancer");
    S = new Vertex<String>("Smoking");
    hLC = new Vertex<String>("Has Lung Cancer");
    hB = new Vertex<String>("Has Bronchitis");
    XR = new Vertex<String>("XRay Result");
    D = new Vertex<String>("Dyspnea"); // shortness of breath

    graph.insertEdge(new Edge<String>(VtA, hTB, EdgeType.TO)); // VtA -> hTB
    graph.insertEdge(new Edge<String>(hTB, TBoC, EdgeType.TO)); // hTB -> TBoC
    graph.insertEdge(new Edge<String>(S, hLC, EdgeType.TO)); // S -> hLC
    graph.insertEdge(new Edge<String>(S, hB, EdgeType.TO)); // S -> hB
    graph.insertEdge(new Edge<String>(TBoC, XR, EdgeType.TO)); // TBoC -> XR
    graph.insertEdge(new Edge<String>(TBoC, D, EdgeType.TO)); // TBoC -> D
    graph.insertEdge(new Edge<String>(hB, D, EdgeType.TO)); // hB -> D
    graph.insertEdge(new Edge<String>(hLC, TBoC, EdgeType.TO)); // hLC -> TBoC
  }

  @Test
  public void testCase01() {
    // d-sep(XR, hLC | {})
    ArrayList<Path> paths = new ArrayList<Path>();

    // Path 1
    Path path1 = new Path();
    // Start Point - XRay Result
    path1.addVertex(XR);
    path1.addVertex(TBoC);
    // End Vertex - has Lung Cancer
    path1.addVertex(hLC);
    paths.add(path1);

    // Path 2
    Path path2 = new Path();
    // Start Point - XRay Result
    path2.addVertex(XR);
    path2.addVertex(TBoC);
    path2.addVertex(D);
    path2.addVertex(hB);
    path2.addVertex(S);
    // End Vertex - has Lung Cancer
    path2.addVertex(hLC);
    paths.add(path2);

    // No evidence
    ArrayList<Vertex<String>> evidence = new ArrayList<Vertex<String>>();

    DSeparation<String> submission = new DSeparation<String>(graph);
    boolean result = submission.dSeparation(paths, evidence);

    // XRay Results may influence if the patient has Lung Cancer
    assertFalse(result);
  }

  @Test
  public void testCase02() {
    // d-sep(XR, hLC | {S})
    ArrayList<Path> paths = new ArrayList<Path>();

    // Path 1
    Path path1 = new Path();
    // Start Point - XRay Result
    path1.addVertex(XR);
    path1.addVertex(TBoC);
    // End Vertex - has Lung Cancer
    path1.addVertex(hLC);
    paths.add(path1);

    // Path 2
    Path path2 = new Path();
    // Start Point - XRay Result
    path2.addVertex(XR);
    path2.addVertex(TBoC);
    path2.addVertex(D);
    path2.addVertex(hB);
    path2.addVertex(S);
    // End Vertex - has Lung Cancer
    path2.addVertex(hLC);
    paths.add(path2);

    // Evidence - Patient's Smoker status
    ArrayList<Vertex<String>> evidence = new ArrayList<Vertex<String>>();
    evidence.add(S);

    DSeparation<String> submission = new DSeparation<String>(graph);
    boolean result = submission.dSeparation(paths, evidence);

    // If we observe the patient's Smoker status, then the patient's XRay Results
    // may provide us with more information about whether they have Lung Cancer.
    assertFalse(result);
  }

  @Test
  public void testCase03() {
    // d-sep(VtA, hB | {})
    ArrayList<Path> paths = new ArrayList<Path>();

    // Path 1
    Path path1 = new Path();
    // Start Point - Visit to Asia
    path1.addVertex(VtA);
    path1.addVertex(hTB);
    path1.addVertex(TBoC);
    path1.addVertex(hLC);
    path1.addVertex(S);
    // End Vertex - has Bronchitis
    path1.addVertex(hB);
    paths.add(path1);

    // Path 2
    Path path2 = new Path();
    // Start Point - Visit to Asia
    path2.addVertex(VtA);
    path2.addVertex(hTB);
    path2.addVertex(TBoC);
    path2.addVertex(D);
    // End Vertex - has Bronchitis
    path2.addVertex(hB);
    paths.add(path2);

    // No Evidence
    ArrayList<Vertex<String>> evidence = new ArrayList<Vertex<String>>();

    DSeparation<String> submission = new DSeparation<String>(graph);
    boolean result = submission.dSeparation(paths, evidence);

    // Learning if the Patient has Visited Asia has no influence on
    // their has Bronchitis status because other unobserved variables
    // may also influence it.
    assertTrue(result);
  }

  @Test
  public void testCase04() {
    // d-sep(VtA, hB | {D})
    ArrayList<Path> paths = new ArrayList<Path>();

    // Path 1
    Path path1 = new Path();
    // Start Point - Visit to Asia
    path1.addVertex(VtA);
    path1.addVertex(hTB);
    path1.addVertex(TBoC);
    path1.addVertex(hLC);
    path1.addVertex(S);
    // End Vertex - has Bronchitis
    path1.addVertex(hB);
    paths.add(path1);

    // Path 2
    Path path2 = new Path();
    // Start Point - Visit to Asia
    path2.addVertex(VtA);
    path2.addVertex(hTB);
    path2.addVertex(TBoC);
    path2.addVertex(D);
    // End Vertex - has Bronchitis
    path2.addVertex(hB);
    paths.add(path2);

    // Evidence - Dyspnea
    ArrayList<Vertex<String>> evidence = new ArrayList<Vertex<String>>();
    evidence.add(D);

    DSeparation<String> submission = new DSeparation<String>(graph);
    boolean result = submission.dSeparation(paths, evidence);

    /// If we observe the patient's Dyspnea status, then the patient's travel history
    // may provide us with more information about whether they have Bronchitis.
    assertFalse(result);
  }

  @Test
  public void testCase05() {
    // d-sep(S, hTB | {})
    ArrayList<Path> paths = new ArrayList<Path>();

    // Path 1
    Path path1 = new Path();
    // Start Point - Smoker
    path1.addVertex(S);
    path1.addVertex(hLC);
    path1.addVertex(TBoC);
    // End Vertex - has Tuberculosis
    path1.addVertex(hTB);
    paths.add(path1);

    // Path 2
    Path path2 = new Path();
    // Start Point - Smoker
    path2.addVertex(S);
    path2.addVertex(hB);
    path2.addVertex(D);
    path2.addVertex(TBoC);
    // End Vertex - has Tuberculosis
    path2.addVertex(hTB);
    paths.add(path2);

    // No evidence
    ArrayList<Vertex<String>> evidence = new ArrayList<Vertex<String>>();

    DSeparation<String> submission = new DSeparation<String>(graph);
    boolean result = submission.dSeparation(paths, evidence);

    // Learning if the patient is a Smoker status, with no other observations,
    // will not influence our understanding about whether the patient has Tuberculosis.
    assertTrue(result);
  }
}
