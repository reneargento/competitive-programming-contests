package uri.christmas.contest2020;

import java.util.*;

/**
 * Created by Rene Argento on 19/12/20.
 */
// https://www.urionlinejudge.com.br/judge/pt/challenges/view/550/9
public class FabricaDeBrinquedosDoPapaiNoel {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int target = scanner.nextInt() - 1;
            target = Math.max(target, 0);
            int vertices = target + 1;
            int edges = scanner.nextInt();

            FlowNetwork flowNetwork = new FlowNetwork(vertices);

            for (int i = 0; i < edges; i++) {
                int vertex1 = scanner.nextInt() - 1;
                int vertex2 = scanner.nextInt() - 1;
                int capacity = scanner.nextInt();
                if (vertex1 == -1 || vertex2 == -1) {
                    continue;
                }
                flowNetwork.addEdge(new FlowEdge(vertex1, vertex2, capacity));
            }
            EdmondsKarp edmondsKarp = new EdmondsKarp(flowNetwork, 0, target);
            long capacity = (long) edmondsKarp.maxFlowValue;
            if (capacity == 0) {
                System.out.println("Nao eh possivel produzir nenhum brinquedo");
            } else {
                System.out.println(capacity);
            }
        }
    }

    private static class FlowNetwork {
        private final int vertices;
        private int edges;
        private Set<FlowEdge>[] adjacent;

        @SuppressWarnings("unchecked")
        public FlowNetwork(int vertices) {
            if (vertices < 0) {
                throw new IllegalArgumentException("Number of vertices must be nonnegative");
            }

            this.vertices = vertices;
            edges = 0;
            adjacent = new HashSet[vertices];

            for(int vertex = 0; vertex < vertices; vertex++) {
                adjacent[vertex] = new HashSet<>();
            }
        }

        public int vertices() {
            return vertices;
        }

        public int edgesCount() {
            return edges;
        }

        public void addEdge(FlowEdge edge) {
            int vertex1 = edge.from();
            int vertex2 = edge.to();

            adjacent[vertex1].add(edge);
            adjacent[vertex2].add(edge);
            edges++;
        }

        public Iterable<FlowEdge> adjacent(int vertex) {
            return adjacent[vertex];
        }

        public Iterable<FlowEdge> edges() {
            Set<FlowEdge> edges = new HashSet<>();

            for(int vertex = 0; vertex < vertices; vertex++) {
                for(FlowEdge edge : adjacent[vertex]) {
                    if (edge.to() != vertex) {
                        edges.add(edge);
                    }
                }
            }

            return edges;
        }
    }

    private static class FlowEdge {
        private final int vertex1;
        private final int vertex2;
        private final double capacity;
        private double flow;

        // Deal with floating-point roundoff errors
        private static final double FLOATING_POINT_EPSILON = 1E-10;

        public FlowEdge(int vertex1, int vertex2, double capacity) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.capacity = capacity;
            this.flow = 0;
        }

        public int from() {
            return vertex1;
        }

        public int to() {
            return vertex2;
        }

        public double capacity() {
            return capacity;
        }

        public double flow() {
            return flow;
        }

        public void setFlow(double flow) {
            this.flow = flow;
        }

        public int other(int vertex) {
            if (vertex == vertex1) {
                return vertex2;
            } else if (vertex == vertex2) {
                return vertex1;
            } else {
                throw new IllegalArgumentException("Invalid endpoint");
            }
        }

        public double residualCapacityTo(int vertex) {
            if (vertex == vertex1) {        // backward edge
                return flow;
            } else if (vertex == vertex2) { // forward edge
                return capacity - flow;
            } else {
                throw new IllegalArgumentException("Invalid endpoint");
            }
        }

        public void addResidualFlowTo(int vertex, double delta) {
            if (delta < 0) {
                throw new IllegalArgumentException("Delta must be nonnegative");
            }

            if (vertex == vertex1) {        // backward edge
                flow -= delta;
            } else if (vertex == vertex2) { // forward edge
                flow += delta;
            } else {
                throw new IllegalArgumentException("Invalid endpoint");
            }

            // Round flow to 0 or capacity if within floating-point precision
            if (Math.abs(flow) <= FLOATING_POINT_EPSILON) {
                flow = 0;
            }
            if (Math.abs(flow - capacity) <= FLOATING_POINT_EPSILON) {
                flow = capacity;
            }

            if (flow < 0) {
                throw new IllegalArgumentException("Resulting flow cannot be negative");
            }
            if (flow > capacity) {
                throw new IllegalArgumentException("Resulting flow cannot be higher than capacity");
            }
        }
    }

    private static class EdmondsKarp {
        private boolean[] visited;  // Is s -> v path in residual graph?
        private FlowEdge[] edgeTo;  // Last edge on shortest s -> v path
        private double maxFlowValue;

        public EdmondsKarp(FlowNetwork flowNetwork, int source, int target) {
            // Find max flow in flowNetwork from source to target
            while (hasAugmentingPath(flowNetwork, source, target)) {
                // Compute bottleneck capacity
                double bottleneck = Double.POSITIVE_INFINITY;

                for (int vertex = target; vertex != source; vertex = edgeTo[vertex].other(vertex)) {
                    bottleneck = Math.min(bottleneck, edgeTo[vertex].residualCapacityTo(vertex));
                }

                // Augment flow
                for (int vertex = target; vertex != source; vertex = edgeTo[vertex].other(vertex)) {
                    edgeTo[vertex].addResidualFlowTo(vertex, bottleneck);
                }

                maxFlowValue += bottleneck;
            }
        }

        private boolean hasAugmentingPath(FlowNetwork flowNetwork, int source, int target) {
            visited = new boolean[flowNetwork.vertices()];
            edgeTo = new FlowEdge[flowNetwork.vertices()];

            Queue<Integer> queue = new LinkedList<>();

            visited[source] = true;
            queue.offer(source);

            while (!queue.isEmpty() && !visited[target]) {
                int vertex = queue.poll();

                for (FlowEdge edge : flowNetwork.adjacent(vertex)) {
                    int neighbor = edge.other(vertex);

                    if (edge.residualCapacityTo(neighbor) > 0 && !visited[neighbor]) {
                        edgeTo[neighbor] = edge;
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }

            return visited[target];
        }

        public double maxFlowValue() {
            return maxFlowValue;
        }

        // Returns true if the vertex is on the source side of the min cut
        public boolean inCut(int vertex) {
            return visited[vertex];
        }
    }
}
