package dev.skz;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Vertex<K, E extends Edge<K>> {

  private final K key;
  private final List<E> edges;

  public Vertex(K key) {
    edges = new ArrayList<>();
    this.key = key;
  }

  public K getKey() {
    return this.key;
  }

  List<E> getEdges() {
    return edges;
  }

  List<K> getAdjacentVertices() {
    return edges.stream().map(edge -> edge.getOtherVertex(this))
        .collect(Collectors.toList());
  }

  void removeSelfLoops() {
    edges.removeIf(Edge::isSelfLoop);
  }

  void addEdge(E m) {
    edges.add(m);
  }

  void addEdges(List<E> m) {
    edges.addAll(m);
  }

  void removeEdge(E m) {
    edges.remove(m);
  }
}
