package code;
import given.iGraph;
import java.util. *;
public class DirectedWeightedGraph<V> extends iGraph<V> {
  /*
   * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
   * 
   */
  // Stores the adjacency map of vertices and their outgoing edges
  public final Map<V, Map<V, Float>> adjacencyMap;

  public DirectedWeightedGraph(){
    adjacencyMap = new HashMap<>();
  }
  @Override
  public String toString() {
    return "Directed Weighted Graph";
   // return tmp;
  }

  // Adds a vertex to the graph, with no edges
  @Override
  public void insertVertex(V v) {
    // TODO Auto-generated method stub
    if(!adjacencyMap.containsKey(v)) {
      adjacencyMap.put(v, new HashMap<>());
    }
  }

  /*
   * Removes a vertex from the graph. If the vertex is not in the graph, return null.
   * Otherwise, return the removed vertex. Make sure to remove all edges connecting to the vertex too.
  */
  @Override
  public V removeVertex(V v) {
    // TODO Auto-generated method stub
    if (!adjacencyMap.containsKey(v)) {
      return null;
    }
//Remove the vertex and its associated edges
    adjacencyMap.remove(v);
    for (V vertex : adjacencyMap.keySet()) {
      adjacencyMap.get(vertex).remove(v);
    }
    return v;
  }

  /*
   * Return true if there is an edge connecting v1 to v2 (directed edge)
   */
  @Override
  public boolean areAdjacent(V v1, V v2) {
    // TODO Auto-generated method stub
    return adjacencyMap.containsKey(v1) && adjacencyMap.get(v1).containsKey(v2);
  }

  /*
   * Insert a (directed) edge connecting the source vertex to the target vertex. Use 1 for the weight of the edge
   */
  @Override
  public void insertEdge(V source, V target) {
    // TODO Auto-generated method stub
    insertEdge(source, target, 1.0f);

  }

  /*
   * Insert a (directed) edge connecting the source vertex to the target vertex, with the given weight
   */
  @Override
  public void insertEdge(V source, V target, float weight) {
    // TODO Auto-generated method stub
    if (adjacencyMap.containsKey(source) && adjacencyMap.containsKey(target)) {
      adjacencyMap.get(source).put(target, weight);
    }
  }

  /*
   * Remove the (directed) edge between source and target
   */
  @Override
  public boolean removeEdge(V source, V target) {
    // TODO Auto-generated method stub
    if (adjacencyMap.containsKey(source) && adjacencyMap.containsKey(target)) {
      return adjacencyMap.get(source).remove(target) != null;
    }
    return false;
  }

  /*
   * Get the weight of the (directed) edge from source to target
   */
  @Override
  public float getEdgeWeight(V source, V target) {
    // TODO Auto-generated method stub
    if (adjacencyMap.containsKey(source) && adjacencyMap.containsKey(target)) {
      Map<V, Float> edges = adjacencyMap.get(source);
      return edges.getOrDefault(target, 0.0f);
    }
    return 0.0f;
  }
  /*
   * return the number of vertices in the graph
   */
  @Override
  public int numVertices() {
    // TODO Auto-generated method stub
    return adjacencyMap.size();
  }
  /*
   * Return an iterable of all the vertices in the graph
   */
  @Override
  public Iterable<V> vertices() {
    // TODO Auto-generated method stub
    return adjacencyMap.keySet();
  }
  /*
   * Return the number of edges in the graph
   */
  @Override
  public int numEdges() {
    // TODO Auto-generated method stub
    int count = 0;
    for (Map<V, Float> edges : adjacencyMap.values()) {
      count += edges.size();
    }
    return count;
  }

  @Override
  public boolean isDirected() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isWeighted() {
    // TODO Auto-generated method stub
    return true;
  }

  /*
   * Return the number of edges leading out of the vertex v
   */
  @Override
  public int outDegree(V v) {
    // TODO Auto-generated method stub
    if (adjacencyMap.containsKey(v)) {
      return adjacencyMap.get(v).size();
    }
    return 0;
  }

  /*
   * Return the number of edges entering the vertex v
   */
  @Override
  public int inDegree(V v) {
    // TODO Auto-generated method stub
    int count = 0;
    for (Map<V, Float> edges : adjacencyMap.values()) {
      if (edges.containsKey(v)) {
        count++;
      }
    }
    return count;
  }

  /*
   * Return an iterable of all outgoing neighbors of the vertex v
   */
  @Override
  public Iterable<V> outgoingNeighbors(V v) {
    // TODO Auto-generated method stub
    if (adjacencyMap.containsKey(v)) {
      return adjacencyMap.get(v).keySet();
    }
    return Collections.emptySet();
  }

  /*
   * Return an iterable of all incoming neighbors of the vertex v
   */
  @Override
  public Iterable<V> incomingNeighbors(V v) {
    // TODO Auto-generated method stub
    List<V> neighbors = new ArrayList<>();
    for (V vertex : adjacencyMap.keySet()) {
      if(adjacencyMap.get(vertex).containsKey(v)) {
        neighbors.add(vertex);
      }
    }
    return neighbors;
  }
}
