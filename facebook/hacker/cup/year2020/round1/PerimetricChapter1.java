package facebook.hacker.cup.year2020.round1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rene Argento on 29/06/19.
 */
// https://www.facebook.com/codingcompetitions/hacker-cup/2020/round-1/problems/A1
public class PerimetricChapter1 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2020/Round 1/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "perimetric_chapter_1_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "perimetric_chapter_1_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "perimetric_chapter_1_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "perimetric_chapter_1_validation_output.txt";
//
    private static final String FILE_INPUT_PATH = PATH + "perimetric_chapter_1_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "perimetric_chapter_1_output.txt";

    public static long computePerimeterProduct(int rooms, List<Integer> xCoordinates, List<Integer> heights, int width,
                                               int aX, int bX, int cX, int dX, int aHeight, int bHeight, int cHeight,
                                               int dHeight) {
        long perimeterProduct = 1;
        long previousX2 = -1;
        long previousX1 = -1;
        long previousHeight2 = -1;
        long previousHeight1 = -1;

        long previousRoomEnd = -1;
        long previousPerimeter = 0;

        Map<Long, Long> maxHeight = new HashMap<>();

        for (int r = 0; r < rooms; r++) {
            long currentX;
            long currentHeight;

            if (r < xCoordinates.size()) {
                currentX = xCoordinates.get(r);
                currentHeight = heights.get(r);
            } else {
                currentX = ((aX * previousX2 + bX * previousX1 + cX) % dX) + 1;
                currentHeight = ((aHeight * previousHeight2 + bHeight * previousHeight1 + cHeight) % dHeight) + 1;
            }

            long perimeter;
            if (previousRoomEnd < currentX) {
                // No intersection
                perimeter = width * 2 + currentHeight * 2;
                perimeter += previousPerimeter;
            } else {
                long deltaWidth = currentX - previousX1;

                long currentMaxHeightStart = maxHeight.getOrDefault(currentX, 0L);
                long currentMaxHeightEnd = maxHeight.getOrDefault(currentX + width, 0L);
                long startHeightDelta = 0;
                long endHeightDelta = 0;

                if (currentHeight > currentMaxHeightStart) {
                    startHeightDelta = currentHeight - currentMaxHeightStart;
                }

                long maxHeightEnd = Math.max(currentMaxHeightEnd, currentMaxHeightStart);
                if (currentHeight > maxHeightEnd) {
                    endHeightDelta = currentHeight - maxHeightEnd;
                }

                perimeter = previousPerimeter + deltaWidth * 2 + startHeightDelta + endHeightDelta;
            }
            perimeterProduct = (perimeterProduct * (perimeter % 1000000007)) % 1000000007;
            previousRoomEnd = currentX + width;
            previousPerimeter = perimeter;

            previousHeight2 = previousHeight1;
            previousHeight1 = currentHeight;
            previousX2 = previousX1;
            previousX1 = currentX;

            for (long nextX = currentX; nextX <= currentX + width; nextX++) {
                long currentMaxHeight = maxHeight.getOrDefault(nextX, 0L);
                long newMaxHeight = Math.max(currentMaxHeight, currentHeight);
                maxHeight.put(nextX, newMaxHeight);
            }
        }

        return perimeterProduct;
    }

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static void test() {
        List<Integer> xCoordinates1 = new ArrayList<>();
        xCoordinates1.add(1);
        xCoordinates1.add(2);
        List<Integer> heights1 = new ArrayList<>();
        heights1.add(3);
        heights1.add(3);
        long perimeter1 = computePerimeterProduct(2, xCoordinates1, heights1, 2, 0, 0, 0, 100,
                0, 0, 0, 100);
        System.out.println("Perimeter 1: " + perimeter1);
        System.out.println("Expected: 120");

        List<Integer> xCoordinates2 = new ArrayList<>();
        xCoordinates2.add(10);
        xCoordinates2.add(20);
        List<Integer> heights2 = new ArrayList<>();
        heights2.add(3);
        heights2.add(3);
        long perimeter2 = computePerimeterProduct(2, xCoordinates2, heights2, 2, 0, 0, 0, 100,
                0, 0, 0, 100);
        System.out.println("\nPerimeter 2: " + perimeter2);
        System.out.println("Expected: 200");

        List<Integer> xCoordinates3 = new ArrayList<>();
        xCoordinates3.add(2);
        xCoordinates3.add(4);
        xCoordinates3.add(5);
        xCoordinates3.add(9);
        xCoordinates3.add(12);
        List<Integer> heights3 = new ArrayList<>();
        heights3.add(4);
        heights3.add(3);
        heights3.add(6);
        heights3.add(3);
        heights3.add(2);
        long perimeter3 = computePerimeterProduct(5, xCoordinates3, heights3, 3, 0, 0, 0, 100,
                0, 0, 0, 100);
        System.out.println("\nPerimeter 3: " + perimeter3);
        System.out.println("Expected: 9144576");

        List<Integer> xCoordinates4 = new ArrayList<>();
        xCoordinates4.add(9);
        xCoordinates4.add(14);
        xCoordinates4.add(15);
        List<Integer> heights4 = new ArrayList<>();
        heights4.add(12);
        heights4.add(7);
        heights4.add(16);
        long perimeter4 = computePerimeterProduct(10, xCoordinates4, heights4, 8, 0, 1, 3, 53,
                5, 2, 1, 38);
        System.out.println("\nPerimeter 4: " + perimeter4);
        System.out.println("Expected: 803986060");

        List<Integer> xCoordinates5 = new ArrayList<>();
        xCoordinates5.add(4);
        xCoordinates5.add(9);
        xCoordinates5.add(10);
        xCoordinates5.add(26);
        xCoordinates5.add(28);
        xCoordinates5.add(59);
        xCoordinates5.add(97);
        xCoordinates5.add(100);
        xCoordinates5.add(105);
        xCoordinates5.add(106);
        List<Integer> heights5 = new ArrayList<>();
        heights5.add(130);
        heights5.add(12);
        heights5.add(82);
        heights5.add(487);
        heights5.add(12);
        heights5.add(30);
        heights5.add(214);
        heights5.add(104);
        heights5.add(104);
        heights5.add(527);
        long perimeter5 = computePerimeterProduct(50, xCoordinates5, heights5, 17, 1, 0, 7, 832,
                21, 81, 410, 605);
        System.out.println("\nPerimeter 5: " + perimeter5);
        System.out.println("Expected: 271473330");

        List<Integer> xCoordinates6 = new ArrayList<>();
        xCoordinates6.add(1);
        xCoordinates6.add(3);
        List<Integer> heights6 = new ArrayList<>();
        heights6.add(100000000);
        heights6.add(300000000);
        long perimeter6 = computePerimeterProduct(200000, xCoordinates6, heights6, 20, 0, 1, 100,
                500000000, 0, 1, 100, 500000000);
        System.out.println("\nPerimeter 6: " + perimeter6);
        System.out.println("Expected: 330339539");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String[] line1 = lines.get(i++).split(" ");
            int rooms = Integer.parseInt(line1[0]);
            int k = Integer.parseInt(line1[1]);
            int width = Integer.parseInt(line1[2]);

            String[] xCoordinatesLine = lines.get(i++).split(" ");
            List<Integer> xCoordinates = new ArrayList<>();
            for (int x = 0; x < k; x++) {
                xCoordinates.add(Integer.parseInt(xCoordinatesLine[x]));
            }

            String[] xCoordinatesAdditionLine = lines.get(i++).split(" ");
            int aX = Integer.parseInt(xCoordinatesAdditionLine[0]);
            int bX = Integer.parseInt(xCoordinatesAdditionLine[1]);
            int cX = Integer.parseInt(xCoordinatesAdditionLine[2]);
            int dX = Integer.parseInt(xCoordinatesAdditionLine[3]);

            String[] heightsLine = lines.get(i++).split(" ");
            List<Integer> heights = new ArrayList<>();
            for (int h = 0; h < k; h++) {
                heights.add(Integer.parseInt(heightsLine[h]));
            }

            String[] heightCoordinatesAdditionLine = lines.get(i).split(" ");
            int aHeight = Integer.parseInt(heightCoordinatesAdditionLine[0]);
            int bHeight = Integer.parseInt(heightCoordinatesAdditionLine[1]);
            int cHeight = Integer.parseInt(heightCoordinatesAdditionLine[2]);
            int dHeight = Integer.parseInt(heightCoordinatesAdditionLine[3]);

            long perimeter = computePerimeterProduct(rooms, xCoordinates, heights, width, aX, bX, cX, dX, aHeight, bHeight,
                    cHeight, dHeight);
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
