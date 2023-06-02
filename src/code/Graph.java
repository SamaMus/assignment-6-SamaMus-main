package code;

public interface Graph<V> {
    void insertVertex(V v);

    V removeVertex(V v);

    boolean areAdjacent(V v1, V v2);

    void insertEdge(V source, V target);

    void insertEdge(V source, V target, float weight);

    boolean removeEdge(V source, V target);

    float getEdgeWeight(V source, V target);

    int numVertices();

    Iterable<V> vertices();

    int numEdges();

    boolean isDirected();

    boolean isWeighted();

    int outDegree(V v);

    int inDegree(V v);

    Iterable<V> outgoingNeighbors(V v);

    Iterable<V> incomingNeighbors(V v);
}
