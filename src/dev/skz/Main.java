package dev.skz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    // EXAMPLE GRAPH
		// UndirectedGraph<Integer> graph = new UndirectedGraph<>();
    // graph.addVertex(0);
    // graph.addVertex(1);
    // graph.addVertex(2);
    // graph.addVertex(3);
    // graph.addVertex(4);
    //
    // graph.addEdge(0,1);
    // graph.addEdge(1, 2);
    // graph.addEdge(2,3);
    // graph.addEdge(2,4);
    //
    // graph.printGraph();

    UndirectedGraph<Integer> graph = readUndirectedGraphFromFile();
    graph.printGraph();

    int minCuts = MinCutCounter.count(graph);
    System.out.println("Num Min Cuts " + minCuts);
  }

  private static UndirectedGraph<Integer> readUndirectedGraphFromFile() {
    List<VertexAdjacencyList<Integer>> adjacencyList = new ArrayList<>();
    try {
      List<String> allLines = Files.readAllLines(Paths.get("kargerMinCut.txt"));
      for (String line : allLines) {
        List<Integer> adjList = Arrays.stream(line.split("\t")).map(Integer::parseInt).collect(
            Collectors.toList());
        int label = adjList.remove(0);
        adjacencyList.add(new VertexAdjacencyList<>(label, adjList));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new UndirectedGraph<>(adjacencyList);
  }
}
