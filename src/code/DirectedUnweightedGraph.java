package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectedUnweightedGraph<V> extends DirectedWeightedGraph<V> {
  /*
   * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
   *
   */

  @Override
  public String toString() {
    return "Directed Unweighted Graph";

  }

  @Override
  public void insertVertex(V v) {
    // TODO Auto-generated method stub
    adjacencyMap.put(v, new HashMap<>());
  }

  @Override
  public V removeVertex(V v) {
    // TODO Auto-generated method stub
    if (!adjacencyMap.containsKey(v)) {
      return null;
    }
    adjacencyMap.remove(v);
    for (V vertex : adjacencyMap.keySet()) {
      adjacencyMap.get(vertex).remove(v);
    }

    return v;
  }

  @Override
  public boolean areAdjacent(V v1, V v2) {
    // TODO Auto-generated method stub
    if (!adjacencyMap.containsKey(v1) || !adjacencyMap.containsKey(v2)) {
      return false;
    }
    return adjacencyMap.get(v1).containsKey(v2);
  }

  @Override
  public void insertEdge(V source, V target) {
    // TODO Auto-generated method stub
    if (!adjacencyMap.containsKey(source) || !adjacencyMap.containsKey(target)) {
      throw new IllegalArgumentException("Invalid vertices");
    }

    adjacencyMap.get(source).put(target, 1.0f);
  }


  @Override
  public void insertEdge(V source, V target, float weight) {
    // TODO Auto-generated method stub
    // For an unweighted graph, the weight is always 1
    insertEdge(source, target);
  }

  @Override
  public boolean removeEdge(V source, V target) {
    // TODO Auto-generated method stub
    if (!adjacencyMap.containsKey(source) || !adjacencyMap.containsKey(target)) {
      return false;
    }
    Map<V, Float> edges = adjacencyMap.get(source);
    if (edges.containsKey(target)) {
      edges.remove(target);
      return true;
    }
    return false;
  }

  @Override
  public float getEdgeWeight(V source, V target) {
    // TODO Auto-generated method stub
    // For an unweighted graph, all edges have a weight of 1
    if (areAdjacent(source, target)) {
      return 1.0f;
    }
    return 0.0f;
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
    int edgeCount = 0;
    for (Map<V, Float> edges : adjacencyMap.values()) {
      edgeCount += edges.size();
    }
    return edgeCount;
  }

  @Override
  public boolean isDirected() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isWeighted() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int outDegree(V v) {
    // TODO Auto-generated method stub
    if (!adjacencyMap.containsKey(v)) {
      throw new IllegalArgumentException("Vertex not found: " + v);
    }
    return adjacencyMap.get(v).size();
  }

  @Override
  public int inDegree(V v) {
    // TODO Auto-generated method stub
    if (!adjacencyMap.containsKey(v)) {
      throw new IllegalArgumentException("Vertex not found: " + v);
    }

    int inDegree = 0;
    for (Map<V, Float> edges : adjacencyMap.values()) {
      if (edges.containsKey(v)) {
        inDegree++;
      }
    }
    return inDegree;
  }

  @Override
  public Iterable<V> outgoingNeighbors(V v) {
    // TODO Auto-generated method stub
    if (!adjacencyMap.containsKey(v)) {
      throw new IllegalArgumentException("Vertex not found: " + v);
    }
    return adjacencyMap.get(v).keySet();
  }


  @Override
  public Iterable<V> incomingNeighbors(V v) {
    // TODO Auto-generated method stub
    if (!adjacencyMap.containsKey(v)) {
      throw new IllegalArgumentException("Vertex not found: " + v);
    }

    List<V> incomingNeighbors = new ArrayList<>();
    for (Map.Entry<V, Map<V, Float>> entry : adjacencyMap.entrySet()) {
      V vertex = entry.getKey();
      Map<V, Float> edges = entry.getValue();

      if (edges.containsKey(v)) {
        incomingNeighbors.add(vertex);
      }
    }
    return incomingNeighbors;
  }
}
