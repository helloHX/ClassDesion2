package tool;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import java.util.*;

public class WeightedGraph<V> extends AbstractGraph<V> {
  // Priority adjacency lists
  private List<PriorityQueue<WeightedEdge>> queues
    = new ArrayList<PriorityQueue<WeightedEdge>>();

  /** Construct a WeightedGraph from edges and vertices in arrays */
  public WeightedGraph(){
  }
  
  /** Construct a WeightedGraph from edges and vertices in arrays */
  public WeightedGraph(int[][] edges, V[] vertices) {
    super(edges, vertices);
    createQueues(edges, vertices.length);
  }

  /** Construct a WeightedGraph from edges and vertices in List */
  public WeightedGraph(int[][] edges, int numberOfVertices) {
    super(edges, numberOfVertices);
    createQueues(edges, numberOfVertices);
  }

  /** Construct a WeightedGraph for vertices 0, 1, 2 and edge list */
  public WeightedGraph(List<WeightedEdge> edges, List<V> vertices) {
    super((List)edges, vertices);
    createQueues(edges, vertices.size());
  }

  /** Construct a WeightedGraph from vertices 0, 1, and edge array */
  public WeightedGraph(List<WeightedEdge> edges,
      int numberOfVertices) {
    super((List)edges, numberOfVertices);
    createQueues(edges, numberOfVertices);
  }

  /** Create priority adjacency lists from edge arrays */
  private void createQueues(int[][] edges, int numberOfVertices) {
    for (int i = 0; i < numberOfVertices; i++) {
      queues.add(new PriorityQueue<WeightedEdge>()); // Create a queue
    }

    for (int i = 0; i < edges.length; i++) {
      int u = edges[i][0];
      int v = edges[i][1];
      int weight = edges[i][2];
      // Insert an edge into the queue
      queues.get(u).offer(new WeightedEdge(u, v, weight));
    }
  }

  /** Create priority adjacency lists from edge lists */
  private void createQueues(List<WeightedEdge> edges,
      int numberOfVertices) {
    for (int i = 0; i < numberOfVertices; i++) {
      queues.add(new PriorityQueue<WeightedEdge>()); // Create a queue
    }

    for (WeightedEdge edge: edges) {
      queues.get(edge.u).offer(edge); // Insert an edge into the queue
    }
  }

  /** Display edges with weights */
  public void printWeightedEdges() {
    for (int i = 0; i < queues.size(); i++) {
      System.out.print(getVertex(i) + " (" + i + "): ");
      for (WeightedEdge edge : queues.get(i)) {
        System.out.print("(" + edge.u +
          ", " + edge.v + ", " + edge.weight + ") ");
      }
      System.out.println();
    }
  }

  /** Get the edges from the weighted graph */  
  public List<PriorityQueue<WeightedEdge>> getWeightedEdges() {
    return queues;
  }
  
  /** Clears the weighted graph */
  public void clear() {
    vertices.clear();
    neighbors.clear();
    queues.clear();
  }
  
  /** Add vertices to the weighted graph */  
  public void addVertex(V vertex) {
    super.addVertex(vertex);
    queues.add(new PriorityQueue<WeightedEdge>());
  }

  /** Add edges to the weighted graph */  
  public void addEdge(int u, int v, int weight) {
    super.addEdge(u, v);
    queues.get(u).add(new WeightedEdge(u, v, weight));
    queues.get(v).add(new WeightedEdge(v, u, weight));
  }

  /** Get a minimum spanning tree rooted at vertex 0 */
  public MST getMinimumSpanningTree() {
    return getMinimumSpanningTree(0);
  }
  
  /** Get a minimum spanning tree rooted at a specified vertex */
  public MST getMinimumSpanningTree(int startingIndex) {
    List<Integer> T = new ArrayList<Integer>();
    // T initially contains the startingVertex;
    T.add(startingIndex);

    int numberOfVertices = vertices.size(); // Number of vertices
    int[] parent = new int[numberOfVertices]; // Parent of a vertex
    // Initially set the parent of all vertices to -1
    for (int i = 0; i < parent.length; i++)
      parent[i] = -1;
    double totalWeight = 0; // Total weight of the tree thus far

    // Clone the priority queue, so to keep the original queue intact
    List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

    // All vertices are found?
    while (T.size() < numberOfVertices) {
      // Search for the vertex with the smallest edge adjacent to
      // a vertex in T
      int v = -1;
      double smallestWeight = Double.MAX_VALUE;
      for (int u : T) { 
        while (!queues.get(u).isEmpty() &&
            T.contains(queues.get(u).peek().v)) {
          // Remove the edge from queues[u] if the adjacent
          // vertex of u is already in T
          queues.get(u).remove();
        }

        if (queues.get(u).isEmpty()) {
          continue; // Consider the next vertex in T
        }

        // Current smallest weight on an edge adjacent to u
        WeightedEdge edge = queues.get(u).peek();
        if (edge.weight < smallestWeight) {
          v = edge.v;
          smallestWeight = edge.weight;
          // If v is added to the tree, u will be its parent
          parent[v] = u;
        }
      } // End of for

      if (v != -1) 
        T.add(v); // Add a new vertex to the tree
      else 
        break; // The tree is not connected, a partial MST is found
      
      totalWeight += smallestWeight;
    } // End of while

    return new MST(startingIndex, parent, T, totalWeight);
  }

  /** Clone an array of queues */
  private List<PriorityQueue<WeightedEdge>> deepClone(
    List<PriorityQueue<WeightedEdge>> queues) {
    List<PriorityQueue<WeightedEdge>> copiedQueues =
      new ArrayList<PriorityQueue<WeightedEdge>>();

    for (int i = 0; i < queues.size(); i++) {
      copiedQueues.add(new PriorityQueue<WeightedEdge>());
      for (WeightedEdge e : queues.get(i)) {
        copiedQueues.get(i).add(e);
      }
    }

    return copiedQueues;
  }

  /** MST is an inner class in WeightedGraph */
  public class MST extends Tree {
    private double totalWeight; // Total weight of all edges in the tree

    public MST(int root, int[] parent, List<Integer> searchOrder,
        double totalWeight) {
      super(root, parent, searchOrder);
      this.totalWeight = totalWeight;
    }

    public double getTotalWeight() {
      return totalWeight;
    }
  }

  /** Find single source shortest paths */
  public ShortestPathTree getShortestPath(int sourceIndex) {
    // T stores the vertices whose path found so far
    List<Integer> T = new ArrayList<Integer>();
    // T initially contains the sourceVertex;
    T.add(sourceIndex);

    // vertices is defined in AbstractGraph
    int numberOfVertices = vertices.size();

    // parent[v] stores the previous vertex of v in the path
    int[] parent = new int[numberOfVertices];
    parent[sourceIndex] = -1; // The parent of source is set to -1

    // costs[v] stores the cost of the path from v to the source
    double[] costs = new double[numberOfVertices];
    for (int i = 0; i < costs.length; i++) {
      costs[i] = Double.MAX_VALUE; // Initial cost set to infinity
    }
    costs[sourceIndex] = 0; // Cost of source is 0

    // Get a copy of queues
    List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

    // Expand T
    while (T.size() < numberOfVertices) {
      int v = -1; // Vertex to be determined
      double smallestCost = Double.MAX_VALUE; // Set to infinity
      for (int u : T) {
        while (!queues.get(u).isEmpty() &&
            T.contains(queues.get(u).peek().v)) {
          queues.get(u).remove(); // Remove the vertex in queue for u
        }

        if (queues.get(u).isEmpty()) {
          // All vertices adjacent to u are in T
          continue;
        }

        WeightedEdge e = queues.get(u).peek();
        if (costs[u] + e.weight < smallestCost) {
          v = e.v;
          smallestCost = costs[u] + e.weight;
          // If v is added to the tree, u will be its parent
          parent[v] = u;
        }
      } // End of for

      T.add(v); // Add a new vertex to T
      costs[v] = smallestCost;
    } // End of while

    // Create a ShortestPathTree
    return new ShortestPathTree(sourceIndex, parent, T, costs);
  }

  /** ShortestPathTree is an inner class in WeightedGraph */
  public class ShortestPathTree extends Tree {
    private double[] costs; // costs[v] is the cost from v to source

    /** Construct a path */
    public ShortestPathTree(int source, int[] parent, 
        List<Integer> searchOrder, double[] costs) {
      super(source, parent, searchOrder);
      this.costs = costs;
    }

    /** Return the cost for a path from the root to vertex v */
    public double getCost(int v) {
      return costs[v];
    }

    /** Print paths from all vertices to the source */
    public void printAllPaths() {
      System.out.println("All shortest paths from " +
        vertices.get(getRoot()) + " are:");
      for (int i = 0; i < costs.length; i++) {
        printPath(i); // Print a path from i to the source
        System.out.println("(cost: " + costs[i] + ")"); // Path cost
      }
    }
  }


public int[][] getAdjacencyMatrix() {
	// TODO Auto-generated method stub
	return null;
}

public void printAdjacencyMatrix() {
	// TODO Auto-generated method stub
	
}
}