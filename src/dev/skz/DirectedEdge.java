package dev.skz;

public class DirectedEdge <K> extends Edge<K> {
  public DirectedEdge(K u, K v) {
    super(u, v, Type.DIRECTED);
  }
}
