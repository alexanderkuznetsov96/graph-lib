package dev.skz;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VertexAdjacencyList<K> {

  private final K rootKey;
  private final List<K> adjacentVertices;

  public VertexAdjacencyList(K rootKey, List<K> adjacentVertices) {
    this.rootKey = rootKey;
    this.adjacentVertices = new ArrayList<>(adjacentVertices);
  }

  public VertexAdjacencyList(K rootKey) {
    this.rootKey = rootKey;
    this.adjacentVertices = new ArrayList<>();
  }

  public void addAdjacentVertex(K adjacentKey) {
    adjacentVertices.add(adjacentKey);
  }

  public K getRootKey() {
    return rootKey;
  }

  public List<K> getAdjacentVertices() {
    return adjacentVertices;
  }

  public List<DirectedEdge<K>> getEdgesAsDirected() {
    return adjacentVertices.stream().map(v -> new DirectedEdge<K>(rootKey, v))
        .collect(Collectors.toList());
  }

  public List<UndirectedEdge<K>> getEdgesAsUndirected() {
    return adjacentVertices.stream().map(v -> new UndirectedEdge<K>(rootKey, v))
        .collect(Collectors.toList());
  }
}
