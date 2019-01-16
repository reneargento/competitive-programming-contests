package uri.christmas.contest2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 25/12/18.
 */
// TODO
// https://www.urionlinejudge.com.br/judge/en/challenges/view/412/7
public class SantasReindeers {

    private static class FastReader {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        /** Call this method to initialize reader for InputStream */
        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        /** Get next word */
        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        //Used to check EOF
        //If getLine() == null, it is a EOF
        //Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int reindeers = FastReader.nextInt();
        int sickReindeers = FastReader.nextInt();

        Set<Integer> sickIds = new HashSet<>();
        Set<Edge> removedEdges = new HashSet<>();
        Edge[] edges = new Edge[reindeers - 1];

        for (int s = 0; s < sickReindeers; s++) {
            sickIds.add(FastReader.nextInt());
        }

        UnionFind unionFind = new UnionFind(reindeers, sickIds);

        for (int i = 0; i < reindeers - 1; i++) {
            int room1 = FastReader.nextInt();
            int room2 = FastReader.nextInt();
            int corridor = FastReader.nextInt();

            Edge edge = new Edge(room1, room2, corridor);
            edges[i] = edge;
        }

        Arrays.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge2.cost - edge1.cost;
            }
        });

        for (Edge edge : edges) {
            int room1 = edge.vertex1;
            int room2 = edge.vertex2;

            boolean connected = unionFind.union(room1, room2, sickIds.contains(room1), sickIds.contains(room2));
            if (!connected) {
                removedEdges.add(edge);
            }
        }

        long totalCost = 0;

        for (Edge edge : removedEdges) {
            totalCost += edge.cost;
        }

        System.out.println(totalCost);
    }

    private static class Edge {
        int vertex1;
        int vertex2;
        int cost;

        Edge(int vertex1, int vertex2, int cost) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.cost = cost;
        }

        @Override
        public int hashCode() {
            return vertex1 * 100 + vertex2;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Edge)) {
                return false;
            }

            Edge otherEdge = (Edge) object;
            return vertex1 == otherEdge.vertex1 && vertex2 == otherEdge.vertex2;
        }
    }

    private static class UnionFind {

        private int[] leaders;
        private int[] ranks;
        private int[] sickReindeers;

        private int components;

        public UnionFind(int size, Set<Integer> sickReindeersList) {
            leaders = new int[size];
            ranks = new int[size];
            sickReindeers = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                ranks[i] = 0;

                if (sickReindeersList.contains(i)) {
                    sickReindeers[i] = 1;
                }
            }
        }

        public int count() {
            return components;
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        //O(inverse Ackermann function)
        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }

            return leaders[site] = find(leaders[site]);
        }

        //O(inverse Ackermann function)
        public boolean union(int site1, int site2, boolean isRoom1Sick, boolean isRoom2Sick) {

            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return false;
            }

            if ((isRoom1Sick && sickReindeers[leader2] != 0)
                    || (isRoom2Sick && sickReindeers[leader1] != 0)) {
                return false;
            }

            int sickCount = 0;
            if (isRoom1Sick) sickCount++;
            if (isRoom2Sick) sickCount++;

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
                sickReindeers[leader2] += sickCount;
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
                sickReindeers[leader1] += sickCount;
            } else {
                leaders[leader1] = leader2;
                ranks[leader2]++;
                sickReindeers[leader2] += sickCount;
            }

            components--;
            return true;
        }
    }
}