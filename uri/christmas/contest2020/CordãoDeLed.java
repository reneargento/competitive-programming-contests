package uri.christmas.contest2020;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/12/20.
 */
// https://www.urionlinejudge.com.br/judge/pt/challenges/view/550/5
public class CordãoDeLed {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int segments = scanner.nextInt();
        int unions = scanner.nextInt();

        UnionFind unionFind = new UnionFind(segments);

        for (int i = 0; i < unions; i++) {
            int segment1 = scanner.nextInt() - 1;
            int segment2 = scanner.nextInt() - 1;
            unionFind.union(segment1, segment2);
        }

        System.out.println(unionFind.count() == 1 ? "COMPLETO" : "INCOMPLETO");
    }

    private static class UnionFind {
        private int[] leaders;
        private int[] ranks;

        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                ranks[i] = 0;
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
        public void union(int site1, int site2) {

            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
            } else {
                leaders[leader1] = leader2;
                ranks[leader2]++;
            }

            components--;
        }

    }

}
