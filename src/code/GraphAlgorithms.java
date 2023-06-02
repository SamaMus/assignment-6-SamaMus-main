package code;
import java.util.*;
import given.iGraph;

/*
 * The class that will hold your graph algorithm implementations
 * Implement:
 * - Depth first search
 * - Breadth first search
 * - Dijkstra's single-source all-destinations shortest path algorithm
 * 
 * Feel free to add any addition methods and fields as you like
 */
public class GraphAlgorithms<V extends Comparable<V>> {
  /*
   * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
   * 
   */
  public static boolean usageCheck = false;
  /*
   * WARNING: MUST USE THIS FUNCTION TO SORT THE 
   * NEIGHBORS (the adjacent call in the pseudocodes)
   * FOR DFS AND BFS
   * 
   * THIS IS DONE TO MAKE AUTOGRADING EASIER
   */
  public Iterable<V> iterableToSortedIterable(Iterable<V> inIterable) {
    usageCheck = true;
    List<V> sorted = new ArrayList<>();
    for (V i : inIterable) {
      sorted.add(i);
    }
    Collections.sort(sorted);
    return sorted;
  }
  /*
   * Runs depth first search on the given graph G and
   * returns a list of vertices in the visited order, 
   * starting from the startvertex.
   * 
   */
  public List<V> DFS(iGraph<V> G, V startVertex) {
    usageCheck = false;
    List<V> visitedOrder = new ArrayList<>();
    Set<V> visited = new HashSet<>();
    depthFirstSearch(G, startVertex, visitedOrder,visited);
    return visitedOrder;
  }
  private void depthFirstSearch(iGraph<V> G, V vertex, List<V> visitedOrder,Set<V> visited) {
    visited.add(vertex);
    visitedOrder.add(vertex);
    Iterable<V> neighbors = iterableToSortedIterable(G.outgoingNeighbors(vertex));
    for (V neighbor : neighbors) {
      if (!visited.contains(neighbor)) {
        depthFirstSearch(G, neighbor, visitedOrder, visited);
      }
    }
  }
  /*
   * Runs Dijkstras single source all-destinations shortest path 
   * algorithm on the given graph G and returns a map of vertices
   * and their associated minimum costs, starting from the startvertex.
   * 
   */
  public HashMap<V,Float> Dijkstras(iGraph<V> G, V startVertex) {
    usageCheck = false;
    HashMap<V, Float> shortestPaths = new HashMap<>();
    PriorityQueue<Node> pq = new PriorityQueue<>();
    pq.add(new Node(startVertex, 0.0f));

    while (!pq.isEmpty()) {
      Node node = pq.poll();
      V vertex = node.vertex;
      float distance = node.distance;

      if (shortestPaths.containsKey(vertex)) {
        continue;
      }
      shortestPaths.put(vertex, distance);

      Iterable<V> neighbors = iterableToSortedIterable(G.outgoingNeighbors(vertex));
      for (V neighbor : neighbors) {
        float edgeWeight = G.getEdgeWeight(vertex, neighbor);
        if (!shortestPaths.containsKey(neighbor)) {
          pq.add(new Node(neighbor, distance + edgeWeight));
        }
      }
    }
    return shortestPaths;
  }
  /*
   *  Returns true if the given graph is cyclic, false otherwise
   */
  public boolean isCyclic(iGraph<V> G) {
Set<V> visited = new HashSet<>();
Set<V> recursionStack = new HashSet<>();

for (V vertex : G.vertices()) {
  if (isCyclicUtil(G, vertex, visited, recursionStack)) {
    return true;
  }
}
  return false;
  }
private  boolean isCyclicUtil(iGraph<V> G, V vertex, Set<V> visited, Set<V> recursionStack) {
    visited.add(vertex);
    recursionStack.add(vertex);

    Iterable<V> neighbors = iterableToSortedIterable(G.outgoingNeighbors(vertex));
    for (V neighbor: neighbors) {
      if (!visited.contains(neighbor)) {
        if (isCyclicUtil(G,neighbor, visited, recursionStack)) {
          return true;
        }
      }
      else if (recursionStack.contains(neighbor)) {
        return true;
      }
    }
    recursionStack.remove(vertex);
    return false;
}
private class Node implements Comparable<Node> {
    V vertex;
    float distance;
    Node (V vertex, float distance) {
      this.vertex = vertex;
      this.distance = distance;
    }
    @Override
  public int compareTo(Node other) {
      return Float.compare(distance, other.distance);
    }
}
}
