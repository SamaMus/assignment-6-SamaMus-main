package given;

import java.util.HashMap;
import java.util.List;

import autograder.Autograder;
import code.*;

/*
 * NOTE: This testing is not comprehensive at all.
 * We will probably use other graphs to test your algorithms so do not trust this by itself!
 */

public class AlgTesting {

  private static float grade = 0.0f;
  
  public static <T> boolean compareListAndArray(List<T> l, T objs[]) {
    if(l.size() != objs.length)
      return false;
    
    for(int i = 0; i < objs.length; i++) {
      if(objs[i] != l.get(i))
        return false;
    }
    return true;
  }
  
  public static void testDFS(GraphAlgorithms<Character> algs, iGraph<Character> G) {
    G.insertVertex('A');
    G.insertVertex('B');
    G.insertVertex('C');
    G.insertVertex('D');
    G.insertVertex('E');
    G.insertVertex('F');
    G.insertVertex('G');
    G.insertVertex('H');
    
    G.insertEdge('A', 'B', 6.9f);
    G.insertEdge('A', 'F', 4.8f);
    G.insertEdge('A', 'H', 1.0f);
    G.insertEdge('B', 'F', 8.4f);
    G.insertEdge('B', 'E', 3.6f);
    G.insertEdge('B', 'G', 2.4f);
    G.insertEdge('C', 'G', 9.7f); 
    G.insertEdge('C', 'F', 0.2f);
    G.insertEdge('D', 'G', 5.8f);
    G.insertEdge('E', 'F', 5.0f);
    
    List<Character> dfsSequence =  algs.DFS(G, 'A');
    Character[] desiredRecursiveSequence = {'A','B','E','F','G','H'};
    Character[] desiredIterativeSequence = {'A','H','F','B','G','E'};
    
    if(compareListAndArray(dfsSequence, desiredRecursiveSequence) || compareListAndArray(dfsSequence, desiredIterativeSequence)) {
      grade += 5.0f;
    }
    else {
      Autograder.Log("DFS failed for " + G);
    }
  }
  
  public static void testDijk(GraphAlgorithms<Character> algs, iGraph<Character> G) {
    G.insertVertex('A');
    G.insertVertex('B');
    G.insertVertex('C');
    G.insertVertex('D');
    G.insertVertex('E');
    G.insertVertex('F');
    G.insertVertex('G');
    G.insertVertex('H');
    
    G.insertEdge('A', 'B', 6.9f);
    G.insertEdge('A', 'F', 4.8f);
    G.insertEdge('A', 'H', 1.0f);
    G.insertEdge('B', 'F', 8.4f);
    G.insertEdge('B', 'E', 3.6f);
    G.insertEdge('B', 'G', 2.4f);
    G.insertEdge('C', 'G', 9.7f); 
    G.insertEdge('C', 'F', 0.2f);
    G.insertEdge('D', 'G', 5.8f);
    G.insertEdge('E', 'F', 5.0f);
    
    G.insertEdge('A', 'E', 10.6f);
    if(G.isDirected())
      G.insertEdge('F', 'H', 0.1f);
    
    HashMap<Character,Float> costs = algs.Dijkstras(G, 'A');
    HashMap<Character,Float> desiredCosts = new HashMap<Character,Float>();
    if(G.isDirected()) {
      desiredCosts.put('A', 0f);
      desiredCosts.put('B', 6.9f);
      desiredCosts.put('E', 10.5f);
      desiredCosts.put('F', 4.8f);
      desiredCosts.put('G', 9.3f);
      desiredCosts.put('H', 1.0f);
    }
    else {
      desiredCosts.put('A', 0f);
      desiredCosts.put('B', 6.9f);
      desiredCosts.put('C', 5.0f);
      desiredCosts.put('D', 15.1f);
      desiredCosts.put('E', 9.8f);
      desiredCosts.put('F', 4.8f);
      desiredCosts.put('G', 9.3f);
      desiredCosts.put('H', 1.0f);
    }
    
    if(costs.equals(desiredCosts)) {
      grade += 8f;
    }
    else {
      for(Character c : costs.keySet())
        System.out.println(c + " " + costs.get(c) );
      Autograder.Log("Dijkstra failed for " + G);
    }
  }
  
  public static void testCyclic(GraphAlgorithms<Character> algs, iGraph<Character> G) {
    G.insertVertex('A');
    G.insertVertex('B');
    G.insertVertex('C');
    G.insertVertex('D');
    
    G.insertVertex('X');
    G.insertVertex('Y');
    G.insertVertex('Z');
    
    if(!algs.isCyclic(G))
      grade += 0.5f;
    else
      Autograder.Log("Graph with no vertices cannot be cyclic (AlgTesting.java)");
      
    G.insertEdge('A', 'B');
    G.insertEdge('A', 'C');
    
    if(!algs.isCyclic(G))
      grade += 0.5f;
    else {
      Autograder.Log("A tree structure is not cyclic (AlgTesting.java)");
      if(!G.isDirected())
        Autograder.Log("Graph is undirected, you maybe re-checking the immediate parent");
    }
    
    G.insertEdge('B', 'D');
    G.insertEdge('C', 'B');
    
    boolean  tmpB =algs.isCyclic(G);
    
    if(G.isDirected()) {
      if(!tmpB)
        grade += 1f;
      else 
        Autograder.Log("This directed graph is not cyclic (AlgTesting.java)");
    } else {
      if(tmpB)
        grade += 2f;
      else 
        Autograder.Log("This undirected graph is cyclic (AlgTesting.java)");
    }
    
    G.removeEdge('A', 'B');
    tmpB =algs.isCyclic(G);
    if(!tmpB)
      grade += 0.5f;
    else 
      Autograder.Log("This graph is not cyclic (AlgTesting.java)");
    
    G.insertEdge('X','Y');
    G.insertEdge('Y','Z');
    G.insertEdge('Z','X');
    
    tmpB =algs.isCyclic(G);
    if(tmpB)
      grade += 2.5f;
    else 
      Autograder.Log("This graph is cyclic (AlgTesting.java)");
  }
  
  public static void main(String args[]) {
    boolean iDidIt = false;
    if (!Autograder.initializedOnce) {
      Autograder.init();
      iDidIt = true;
    }
    
    Autograder.Log("Starting algorithm testing");
    
    GraphAlgorithms<Character> algs = new GraphAlgorithms<Character>();
    
    //Test DFS with directed unweighted
    DirectedUnweightedGraph<Character> duG = new DirectedUnweightedGraph<Character>();
    try{
      testDFS(algs, duG);
    }catch (Exception e) {
      Autograder.Log("Exception occurred when testing DFS algorithm");
    }
    
    //Test Dijktra with both weighted
    UndirectedWeightedGraph<Character> uwG = new UndirectedWeightedGraph<Character>();
    try {
      testDijk(algs, uwG);
    } catch (Exception e) {
      Autograder.Log("Exception occurred when testing Dijkstra's Algorithm on an Undirected, Weighted Graph");
    }
    DirectedWeightedGraph<Character> dwG = new DirectedWeightedGraph<Character>();
    try {
      testDijk(algs, dwG);
    }catch (Exception e) {
      Autograder.Log("Exception occurred when testing Dijkstra's Algorithm on a Directed, Weighted Graph");
    }
    //Test cyclic 
    duG = new DirectedUnweightedGraph<Character>();
    try {
      testCyclic(algs, duG);
    } catch (Exception e) {
      Autograder.Log("Exception occurred when testing for cycles");
    }
    // uuG = new UndirectedUnweightedGraph<Character>();
    // testCyclic(algs, uuG);
    
    Autograder.Log("Finished algorithm testing");

    Autograder.addGrade((grade / 26.0f) * 40.0f);
    
    if (iDidIt) {
      Autograder.printLog();
      Autograder.printGrade(40);
    }
  }

}
