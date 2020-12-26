package dev.skz;

import java.util.Random;

public class MinCutCounter<K> {

  public static <K> int count(UndirectedGraph<K> g) {
    int n = g.getNumVertices();
    int N = n;
    int minCuts = Integer.MAX_VALUE;
    for (int i = 1; i <= N; i++) {
      minCuts = Math.min(minCuts, countOnce(new UndirectedGraph<K>(g), new Random(i)));
      System.out.println("Trial: " + i + "/" + N + " MinCount: " + minCuts);
    }
    return minCuts;
  }

  public static <K> int countOnce(UndirectedGraph<K> g, Random random) {
    while (g.getNumVertices() > 2) {
      UndirectedEdge<K> randomEdge = getRandomEdge(g, random);
      g.contract(randomEdge);
    }
    return g.getNumEdges();
  }

  public static <K> UndirectedEdge<K> getRandomEdge(UndirectedGraph<K> g, Random random) {
    int n = g.getNumEdges();
    return (UndirectedEdge<K>) g.getEdges().toArray()[random.nextInt(n)];
  }

}
