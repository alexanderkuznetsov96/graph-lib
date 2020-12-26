package dev.skz;

public class UndirectedEdge <K> extends Edge<K> {
  public UndirectedEdge(K u, K v) {
    super(u, v, Type.UNDIRECTED);
  }
}
