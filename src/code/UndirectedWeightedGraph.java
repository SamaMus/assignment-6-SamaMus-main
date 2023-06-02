package code;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UndirectedWeightedGraph<V> implements Graph<V> {
  /*
   * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
   * 
   */
  private final Map<V, Set<Edge<V>>> adjacencyMap;

  public UndirectedWeightedGraph() {
    adjacencyMap = new HashMap<>();
  }
  @Override
  public void insertVertex(V v) {
    if (!adjacencyMap.containsKey(v)) {
      adjacencyMap.put(v, new HashSet<>());
    }
  }
  @Override
  public V removeVertex(V v) {
    Set<Edge<V>> edges = adjacencyMap.remove(v);
    if (edges != null) {
      for (V vertex : vertices()) {
        removeEdge(vertex, v);
      }
      return v;
    }
    return null;
  }
  @Override
  public boolean areAdjacent(V v1, V v2) {
    // TODO Auto-generated method stub
    Set<Edge<V>> edges1 = adjacencyMap.get(v1);
    Set<Edge<V>> edges2 = adjacencyMap.get(v2);
    if (edges1 != null && edges2 != null) {
      for (Edge<V> edge : edges1) {
        if (edge.target.equals(v2)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void insertEdge(V source, V target) {
    // TODO Auto-generated method stub
    insertEdge(source, target, 0);


  }

  @Override
  public void insertEdge(V source, V target, float weight) {
    // TODO Auto-generated method stub

    if (adjacencyMap.containsKey(source) && adjacencyMap.containsKey(target)) {
      Edge<V> edge = new Edge<>(target, weight);
      adjacencyMap.get(source).add(edge);

      // For an undirected graph, we need to add the reverse edge as well
      Edge<V> reverseEdge = new Edge<>(source, weight);
      adjacencyMap.get(target).add(reverseEdge);
    }
  }

  @Override
  public boolean removeEdge(V source, V target) {
    // TODO Auto-generated method stub
    Set<Edge<V>> edges = adjacencyMap.get(source);
    if (edges != null) {
      Edge<V> edgeToRemove = null;
      for (Edge<V> edge : edges) {
        if (edge.target.equals(target)) {
          edgeToRemove = edge;
          break;
        }
      }
      if (edgeToRemove != null) {
        edges.remove(edgeToRemove);

        // Also remove the reverse edge
        Set<Edge<V>> reverseEdges = adjacencyMap.get(target);
        if (reverseEdges != null) {
          for (Edge<V> reverseEdge : reverseEdges) {
            if (reverseEdge.target.equals(source)) {
              reverseEdges.remove(reverseEdge);
              break;
            }
          }
        }

        return true;
      }
    }
    return false;
  }

  @Override
  public float getEdgeWeight(V source, V target) {
    // TODO Auto-generated method stub
    Set<Edge<V>> edges = adjacencyMap.get(source);
    if (edges != null) {
      for (Edge<V> edge : edges) {
        if (edge.target.equals(target)) {
          return edge.weight;
        }
      }
    }
    return Float.POSITIVE_INFINITY;
  }

  @Override
  public int numVertices() {
    // TODO Auto-generated method stub
    return adjacencyMap.size();
  }

  @Override
  public Iterable<V> vertices() {
    // TODO Auto-generated method stub
    return adjacencyMap.keySet();
  }

  @Override
  public int numEdges() {
    // TODO Auto-generated method stub
    int count = 0;
    for (Set<Edge<V>> edges : adjacencyMap.values()) {
      count += edges.size();
    }
    return count / 2;
    // Divide by 2 since each edge is counted twice in an undirected graph
  }

  @Override
  public boolean isDirected() {
    return false;
  }

  @Override
  public boolean isWeighted() {
    return false;
  }

  @Override
  public int outDegree(V v) {
    // TODO Auto-generated method stub
    Set<Edge<V>> edges = adjacencyMap.get(v);
    return edges != null ? edges.size() : 0;
  }

  @Override
  public int inDegree(V v) {
    // TODO Auto-generated method stub
    return outDegree(v);
  }

  @Override
  public Iterable<V> outgoingNeighbors(V v) {
    // TODO Auto-generated method stub
    Set<Edge<V>> edges = adjacencyMap.get(v);
    Set<V> neighbors = new HashSet<>();
    if (edges != null) {
      for (Edge<V> edge : edges) {
        neighbors.add(edge.target);
      }
    }
    return neighbors;
  }

  @Override
  public Iterable<V> incomingNeighbors(V v) {
    // TODO Auto-generated method stub
    return outgoingNeighbors(v);
  }
  @Override
  public String toString() {
    return "Undirected Weighted Graph";
  }
  private static class Edge<V> {
    V target;
    float weight;

    Edge(V target, float weight) {
      this.target = target;
      this.weight = weight;
    }
  }
}
