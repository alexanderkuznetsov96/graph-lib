package dev.skz;

class Edge <K> {

  private final K u;
  private final K v;
  private final Type type;

  public Edge(K u, K v, Type type) {
    this.u = u;
    this.v = v;
    this.type = type;
  }

  public Edge(K u, K v) {
    this(u, v, Type.UNDIRECTED);
  }

  public Edge(Edge<K> other) {
    this(other.getU(), other.getV(), other.getType());
  }

  @Override
  public int hashCode() {
    return u.hashCode() ^ v.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Edge)) {
      return false;
    }

    Edge otherEdge = (Edge) o;

    if (type == Type.DIRECTED) {
      return otherEdge.getU().equals(u) && otherEdge.getV().equals(v);
    }
    return otherEdge.getU().equals(u) && otherEdge.getV().equals(v)
        || otherEdge.getU().equals(v) && otherEdge.getV().equals(u);
  }

  K getU() {
    return u;
  }

  K getV() {
    return v;
  }

  Type getType() {
    return type;
  }

  public boolean isSelfLoop() {
    return u.equals(v);
  }

  public K getOtherVertex(Vertex<K, ?> u) {
    return getOtherVertex(u.getKey());
  }

  public K getOtherVertex(K u) {
    if (u.equals(this.u))
      return v;
    else if (u.equals(this.v)) {
      return this.u;
    } else {
      throw new IllegalArgumentException("Vertex is not found in this edge");
    }
  }

  enum Type {
    UNDIRECTED,
    DIRECTED
  }
}
