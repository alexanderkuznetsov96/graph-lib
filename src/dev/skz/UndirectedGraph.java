package dev.skz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UndirectedGraph<K> {

  private final HashMap<K, UndirectedVertex<K>> vertices;
  private final List<UndirectedEdge<K>> edges;

  public UndirectedGraph() {
    this(new HashMap<>(), new ArrayList<>());
  }

  public UndirectedGraph(UndirectedGraph<K> g) {
    this(g.toAdjacencyList());
  }

  private UndirectedGraph(HashMap<K, UndirectedVertex<K>> vertices,
      List<UndirectedEdge<K>> edges) {
    this.vertices = vertices;
    this.edges = edges;
  }

  public UndirectedGraph(
      List<VertexAdjacencyList<K>> graphAsListOfAdjacencyLists) {
    this();

    HashMap<UndirectedEdge<K>, Integer> edgeCounts = new HashMap<>();

    for (VertexAdjacencyList<K> v : graphAsListOfAdjacencyLists) {
      addVertex(v.getRootKey());

      List<UndirectedEdge<K>> edges = v.getEdgesAsUndirected();

      for (UndirectedEdge<K> edge : edges) {
        edgeCounts.compute(edge, (m, c) -> c == null ? 1 : c + 1);
      }

    }

    for (Map.Entry<UndirectedEdge<K>, Integer> edgeCount : edgeCounts.entrySet()) {
      UndirectedEdge<K> edge = edgeCount.getKey();
      int count = edgeCount.getValue();
      if (count % 2 == 1) {
        throw new IllegalArgumentException(
            "Counts in an undirected must be even because every edge gets counted twice.");
      }
      // Adjacency list double counts edges of an undirected graph, so we cut each sum in half
      count = count >> 1;

      for (int i = 0; i < count; i++) {
        addEdge(edge);
      }
    }
  }

  public void addVertex(K key) {
    vertices.computeIfAbsent(key, UndirectedVertex::new);
  }

  public UndirectedVertex<K> getVertex(K key) {
    return vertices.get(key);
  }

  public void addEdge(K uKey, K vKey) {
    UndirectedEdge<K> edge = new UndirectedEdge<K>(uKey, vKey);
    addEdge(edge);
  }

  public void addEdge(UndirectedEdge<K> edge) {
    Vertex<K, UndirectedEdge<K>> u = getVertex(edge.getU());
    Vertex<K, UndirectedEdge<K>> v = getVertex(edge.getV());
    u.addEdge(edge);
    v.addEdge(edge);
    edges.add(edge);
  }

  public void addEdge(Vertex<K, UndirectedEdge<K>> u, Vertex<K, UndirectedEdge<K>> v) {
    addEdge(u.getKey(), v.getKey());
  }

  public int getNumVertices() {
    return vertices.size();
  }

  public int getNumEdges() {
    return edges.size();
  }

  public Collection<UndirectedVertex<K>> getVertices() {
    return vertices.values();
  }

  public Collection<UndirectedEdge<K>> getEdges() {
    return edges;
  }

  public void printGraph() {
    for (VertexAdjacencyList<K> v : toAdjacencyList()) {
      System.out.print(v.getRootKey() + "   ");
      v.getAdjacentVertices().forEach(u -> System.out.print(u + " "));
      System.out.println();
    }
  }

  public List<VertexAdjacencyList<K>> toAdjacencyList() {
    List<VertexAdjacencyList<K>> adjacencyList = new ArrayList<>();

    for (UndirectedVertex<K> v : vertices.values()) {
      adjacencyList.add(new VertexAdjacencyList<>(v.getKey(), v.getAdjacentVertices()));
    }

    return adjacencyList;
  }

  public void contract(UndirectedEdge<K> edge) {
    UndirectedVertex<K> u = getVertex(edge.getU());
    UndirectedVertex<K> v = getVertex(edge.getV());

    v.removeEdge(edge);
    u.removeEdge(edge);

    edges.remove(edge);

    vertices.remove(v.getKey());

    for (UndirectedEdge<K> m : v.getEdges()) {
      UndirectedVertex<K> adjacentVertex = getVertex(m.getOtherVertex(v));
      addEdge(u, adjacentVertex);
      adjacentVertex.removeEdge(m);
      edges.remove(m);
    }

    u.removeSelfLoops();
    edges.removeIf(Edge::isSelfLoop);
  }

}
