package facebook.hacker.cup.year2020.round1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 29/06/19.
 */
// https://www.facebook.com/codingcompetitions/hacker-cup/2020/round-1/problems/A2
public class PerimetricChapter2 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2020/Round 1/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "perimetric_chapter_2_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "perimetric_chapter_2_sample_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "perimetric_chapter_2_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "perimetric_chapter_2_output.txt";

    public static long computePerimeterProduct(int rooms, long[] xCoordinates, long[] heights, long[] widths,
                                               long aX, long bX, long cX, long dX,
                                               long aWidth, long bWidth, long cWidth, long dWidth,
                                               long aHeight, long bHeight, long cHeight, long dHeight) {
        long perimeterProduct = 1;
        long previousX2 = -1;
        long previousX1 = -1;
        long previousWidth2 = -1;
        long previousWidth1 = -1;
        long previousHeight2 = -1;
        long previousHeight1 = -1;

        long previousPerimeter = 0;

        IntervalBST<Integer> intervalBST = new IntervalBST<>();

        for (int r = 0; r < rooms; r++) {
            long currentX;
            long currentWidth;
            long currentHeight;

            if (r < xCoordinates.length) {
                currentX = xCoordinates[r];
                currentWidth = widths[r];
                currentHeight = heights[r];
            } else {
                currentX = ((aX * previousX2 + bX * previousX1 + cX) % dX) + 1;
                currentWidth = ((aWidth * previousWidth2 + bWidth * previousWidth1 + cWidth) % dWidth) + 1;
                currentHeight = ((aHeight * previousHeight2 + bHeight * previousHeight1 + cHeight) % dHeight) + 1;
            }

            long leftPosition = currentX;
            long rightPosition = currentX + currentWidth;
            IntervalBST.Interval interval = new IntervalBST.Interval(leftPosition, rightPosition);

            List<IntervalBST.Interval> intersections = intervalBST.getAllIntersections(interval);

            long heightReduction = intersections.size() * currentHeight * 2;
            long height = (currentHeight * 2) - heightReduction;
            long width = currentWidth;

            if (!intersections.isEmpty()) {
                width = getDeltaWidth(intersections, interval);

                for (IntervalBST.Interval intersection : intersections) {
                    intervalBST.remove(intersection);
                }
            }
            intervalBST.put(interval, 0);

            long perimeter = previousPerimeter + width * 2 + height;
            perimeterProduct = (perimeterProduct * (perimeter % 1000000007)) % 1000000007;
            previousPerimeter = perimeter;

            previousX2 = previousX1;
            previousX1 = currentX;
            previousWidth2 = previousWidth1;
            previousWidth1 = currentWidth;
            previousHeight2 = previousHeight1;
            previousHeight1 = currentHeight;
        }

        return perimeterProduct;
    }

    private static long getDeltaWidth(List<IntervalBST.Interval> intersections, IntervalBST.Interval interval) {
        long deltaWidth = 0;
        int lastIndex = intersections.size() - 1;

        Collections.sort(intersections);

        long currentValue = intersections.get(0).max;
        if (interval.min < intersections.get(0).min) {
            deltaWidth += intersections.get(0).min - interval.min;
        }

        for (IntervalBST.Interval intersection : intersections) {
            long delta = intersection.min - currentValue;
            if (delta > 0) {
                deltaWidth += delta;
            }
            currentValue = intersection.max;
        }

        long finalDelta = interval.max - intersections.get(lastIndex).max;
        if (finalDelta > 0) {
            deltaWidth += finalDelta;
        }

        long minX = Math.min(interval.min, intersections.get(0).min);
        long maxX = Math.max(interval.max, intersections.get(lastIndex).max);
        interval.min = minX;
        interval.max = maxX;

        return deltaWidth;
    }

    // Interval binary search tree that uses randomization to maintain balance
    private static class IntervalBST<Value> {

        private static class Interval implements Comparable<Interval> {
            long min;
            long max;

            Interval(long min, long max) {
                this.min = min;
                this.max = max;
            }

            public boolean intersects(Interval that) {
                if (this.max < that.min || that.max < this.min) {
                    return false;
                }
                return true;
            }

            @Override
            public int compareTo(Interval that) {
                if (this.min < that.min) {
                    return -1;
                } else if (this.min > that.min) {
                    return 1;
                } else if (this.max < that.max) {
                    return -1;
                } else if (this.max > that.max) {
                    return 1;
                } else {
                    return 0;
                }
            }

            @Override
            public boolean equals(Object other) {
                if (!(other instanceof IntervalBST.Interval)) {
                    return false;
                }

                IntervalBST.Interval otherInterval = (IntervalBST.Interval) other;
                return this.min == otherInterval.min && this.max == otherInterval.max;
            }

            @Override
            public int hashCode() {
                return Double.hashCode(min) * 31 + Double.hashCode(max);
            }
        }

        private Node root;

        private class Node {
            Interval interval;  // key
            Value value;        // associated data
            Node left, right;   // left and right subtrees
            int size;           // size of subtree rooted at this node
            double max;         // max endpoint in subtree rooted at this node

            Node(Interval interval, Value value) {
                this.interval = interval;
                this.value = value;
                this.size = 1;
                this.max = interval.max;
            }
        }

        /***************************************************************************
         *  BST search
         ***************************************************************************/

        public boolean contains(Interval interval) {
            return (get(interval) != null);
        }

        // return value associated with the given key
        // if no such value, return null
        public Value get(Interval interval) {
            return get(root, interval);
        }

        private Value get(Node node, Interval interval) {
            if (node == null) {
                return null;
            }

            int compare = interval.compareTo(node.interval);

            if (compare < 0) {
                return get(node.left, interval);
            } else if (compare > 0) {
                return get(node.right, interval);
            } else {
                return node.value;
            }
        }

        /***************************************************************************
         *  Insertion
         ***************************************************************************/
        public void put(Interval interval, Value value) {
            if (contains(interval)) {
                //remove duplicates
                remove(interval);
            }

            root = randomizedInsert(root, interval, value);
        }

        // make new node the root with uniform probability to keep the BST balanced
        private Node randomizedInsert(Node node, Interval interval, Value value) {
            if (node == null) {
                return new Node(interval, value);
            }

            if (Math.random() * size(node) < 1.0) {
                return rootInsert(node, interval, value);
            }

            int compare = interval.compareTo(node.interval);
            if (compare < 0)  {
                node.left = randomizedInsert(node.left, interval, value);
            } else {
                node.right = randomizedInsert(node.right, interval, value);
            }

            updateSizeAndMax(node);

            return node;
        }

        private Node rootInsert(Node node, Interval interval, Value value) {
            if (node == null) {
                return new Node(interval, value);
            }

            int compare = interval.compareTo(node.interval);
            if (compare < 0) {
                node.left = rootInsert(node.left, interval, value);
                node = rotateRight(node);
            } else {
                node.right = rootInsert(node.right, interval, value);
                node = rotateLeft(node);
            }

            return node;
        }

        /***************************************************************************
         *  Deletion
         ***************************************************************************/

        // Remove and return value associated with given interval;
        // if no such interval exists return null
        public Value remove(Interval interval) {
            Value value = get(interval);
            root = remove(root, interval);
            return value;
        }

        private Node remove(Node node, Interval interval) {
            if (node == null) {
                return null;
            }

            int compare = interval.compareTo(node.interval);
            if (compare < 0) {
                node.left = remove(node.left, interval);
            } else if (compare > 0) {
                node.right = remove(node.right, interval);
            } else {
                node = joinLeftAndRightNodes(node.left, node.right);
            }

            updateSizeAndMax(node);
            return node;
        }

        private Node joinLeftAndRightNodes(Node left, Node right) {
            if (left == null) {
                return right;
            }
            if (right == null) {
                return left;
            }

            if (Math.random() * (size(left) + size(right)) < size(left))  {
                left.right = joinLeftAndRightNodes(left.right, right);
                updateSizeAndMax(left);
                return left;
            } else {
                right.left = joinLeftAndRightNodes(left, right.left);
                updateSizeAndMax(right);
                return right;
            }
        }

        /***************************************************************************
         *  Interval searching
         ***************************************************************************/

        // return an interval in the data structure that intersects the given interval;
        // return null if no such interval exists
        // running time is proportional to log N
        public Interval getIntersection(Interval interval) {
            return getIntersection(root, interval);
        }

        // look in subtree rooted at node
        public Interval getIntersection(Node node, Interval interval) {
            while (node != null) {
                if (interval.intersects(node.interval)) {
                    return node.interval;
                } else if (node.left == null) {
                    node = node.right;
                } else if (node.left.max < interval.min) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            }

            return null;
        }

        // return *all* intervals in the data structure that intersect the given interval
        // running time is proportional to R log N, where R is the number of intersections
        public List<Interval> getAllIntersections(Interval interval) {
            List<Interval> intersections = new ArrayList<>();
            getAllIntersections(root, interval, intersections);
            return intersections;
        }

        // look in subtree rooted at node
        public boolean getAllIntersections(Node node, Interval interval, List<Interval> intersections) {
            boolean foundCenter = false;
            boolean foundLeft = false;
            boolean foundRight = false;

            if (node == null) {
                return false;
            }

            if (interval.intersects(node.interval)) {
                intersections.add(node.interval);
                foundCenter = true;
            }

            if (node.left != null && node.left.max >= interval.min) {
                foundLeft = getAllIntersections(node.left, interval, intersections);
            }

            if (foundLeft || node.left == null || node.left.max < interval.min) {
                foundRight = getAllIntersections(node.right, interval, intersections);
            }

            return foundCenter || foundLeft || foundRight;
        }

        /***************************************************************************
         *  useful binary search tree functions
         ***************************************************************************/

        // return number of nodes in subtree rooted at node
        public int size() {
            return size(root);
        }

        private int size(Node node) {
            if (node == null) {
                return 0;
            } else {
                return node.size;
            }
        }

        // height of tree (empty tree height = 0)
        public int height() {
            return height(root);
        }

        private int height(Node node) {
            if (node == null) {
                return 0;
            }

            return 1 + Math.max(height(node.left), height(node.right));
        }

        /***************************************************************************
         *  helper binary search tree functions
         ***************************************************************************/

        // update tree information (subtree size and max fields)
        private void updateSizeAndMax(Node node) {
            if (node == null) {
                return;
            }

            node.size = 1 + size(node.left) + size(node.right);
            node.max = max3(node.interval.max, max(node.left), max(node.right));
        }

        private double max(Node node) {
            if (node == null) {
                return Double.MIN_VALUE;
            }

            return node.max;
        }

        // precondition: intervalAMax is not null
        private double max3(double intervalAMax, double intervalBMax, double intervalCMax) {
            return Math.max(intervalAMax, Math.max(intervalBMax, intervalCMax));
        }

        private Node rotateRight(Node node) {
            Node newRoot = node.left;
            node.left = newRoot.right;
            newRoot.right = node;

            updateSizeAndMax(node);
            updateSizeAndMax(newRoot);

            return newRoot;
        }

        private Node rotateLeft(Node node) {
            Node newRoot = node.right;
            node.right = newRoot.left;
            newRoot.left = node;

            updateSizeAndMax(node);
            updateSizeAndMax(newRoot);

            return newRoot;
        }
    }

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static void test() {
        long[] xCoordinates1 = {1, 2};
        long[] widths1 = {2, 2};
        long[] heights1 = {3, 3};
        long perimeter1 = computePerimeterProduct(2, xCoordinates1, heights1, widths1, 0, 0, 0, 100,
                0, 0, 0, 100, 0, 0, 0, 100);
        System.out.println("Perimeter 1: " + perimeter1);
        System.out.println("Expected: 120");

        long[] xCoordinates2 = {10, 20};
        long[] widths2 = {2, 2};
        long[] heights2 = {3, 3};
        long perimeter2 = computePerimeterProduct(2, xCoordinates2, heights2, widths2, 0, 0, 0, 100,
                0, 0, 0, 100,
                0, 0, 0, 100);
        System.out.println("\nPerimeter 2: " + perimeter2);
        System.out.println("Expected: 200");

        long[] xCoordinates3 = {8, 3, 10, 16, 2};
        long[] widths3 = {3, 1, 5, 2, 18};
        long[] heights3 = {9, 6, 6, 4, 1};
        long perimeter3 = computePerimeterProduct(5, xCoordinates3, heights3, widths3, 0, 0, 0, 100,
                0, 0, 0, 100, 0, 0, 0, 100);
        System.out.println("\nPerimeter 3: " + perimeter3);
        System.out.println("Expected: 170325120");

        long[] xCoordinates4 = {14, 5, 14};
        long[] widths4 = {6, 4, 13};
        long[] heights4 = {29, 23, 22};
        long perimeter4 = computePerimeterProduct(10, xCoordinates4, heights4, widths4, 4, 7, 2, 47,
                2, 3, 17, 33, 1, 0, 27, 31);
        System.out.println("\nPerimeter 4: " + perimeter4);
        System.out.println("Expected: 81786362");

        long[] xCoordinates5 = {100, 26, 4, 28, 106, 59, 9, 105, 10, 97};
        long[] widths5 = {130, 12, 82, 487, 12, 30, 214, 104, 104, 527};
        long[] heights5 = {938, 903, 899, 899, 896, 857, 841, 837, 834, 834};
        long perimeter5 = computePerimeterProduct(50, xCoordinates5, heights5, widths5, 14, 40, 784, 4832,
                21, 81, 410, 605, 1, 0, 924, 951);
        System.out.println("\nPerimeter 5: " + perimeter5);
        System.out.println("Expected: 950017190");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String[] line1 = lines.get(i++).split(" ");
            int rooms = Integer.parseInt(line1[0]);
            int k = Integer.parseInt(line1[1]);

            String[] xCoordinatesLine = lines.get(i++).split(" ");
            long[] xCoordinates = new long[xCoordinatesLine.length];
            for (int x = 0; x < k; x++) {
                xCoordinates[x] = Integer.parseInt(xCoordinatesLine[x]);
            }

            String[] xCoordinatesAdditionLine = lines.get(i++).split(" ");
            int aX = Integer.parseInt(xCoordinatesAdditionLine[0]);
            int bX = Integer.parseInt(xCoordinatesAdditionLine[1]);
            int cX = Integer.parseInt(xCoordinatesAdditionLine[2]);
            int dX = Integer.parseInt(xCoordinatesAdditionLine[3]);

            String[] widthsLine = lines.get(i++).split(" ");
            long[] widths = new long[xCoordinatesLine.length];
            for (int w = 0; w < k; w++) {
                widths[w] = Integer.parseInt(widthsLine[w]);
            }

            String[] widthsAdditionLine = lines.get(i++).split(" ");
            int aWidth = Integer.parseInt(widthsAdditionLine[0]);
            int bWidth = Integer.parseInt(widthsAdditionLine[1]);
            int cWidth = Integer.parseInt(widthsAdditionLine[2]);
            int dWidth = Integer.parseInt(widthsAdditionLine[3]);

            String[] heightsLine = lines.get(i++).split(" ");
            long[] heights = new long[xCoordinatesLine.length];
            for (int h = 0; h < k; h++) {
                heights[h] = Integer.parseInt(heightsLine[h]);
            }

            String[] heightCoordinatesAdditionLine = lines.get(i).split(" ");
            int aHeight = Integer.parseInt(heightCoordinatesAdditionLine[0]);
            int bHeight = Integer.parseInt(heightCoordinatesAdditionLine[1]);
            int cHeight = Integer.parseInt(heightCoordinatesAdditionLine[2]);
            int dHeight = Integer.parseInt(heightCoordinatesAdditionLine[3]);

            long perimeter = computePerimeterProduct(rooms, xCoordinates, heights, widths, aX, bX, cX, dX,
                    aWidth, bWidth, cWidth, dWidth, aHeight, bHeight, cHeight, dHeight);
            output.add("Case #" + caseId + ": " + perimeter);
            caseId++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private static void writeDataOnFile(String file, List<String> data){
        for(String line : data) {
            writeFileOutput(file, line + "\n");
        }
    }

    private static void writeFileOutput(String file, String data){
        byte[] dataBytes = data.getBytes();

        try {
            Files.write(Paths.get(file), dataBytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
