package facebook.hacker.cup.year2022.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 26/08/22.
 */
public class SecondMeaning1And2 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2022/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "second_meaning_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_meaning_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "second_meaning_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_meaning_validation_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "second_meaning_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_meaning_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "second_second_meaning_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_second_meaning_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "second_second_meaning_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_second_meaning_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "second_second_meaning_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "second_second_meaning_output.txt";

    private static class Node {
        String symbol;
        Node left;
        Node right;

        public Node(String symbol) {
            this.symbol = symbol;
        }
    }

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static List<String> generateCodewords(int numberOfCodewords, String initialCodeword) {
        List<String> codewords = new ArrayList<>();
        Node root = generateTree();
        generateCodewords(numberOfCodewords - 1, initialCodeword, codewords, root, new StringBuilder());
        return codewords;
    }

    private static Node generateTree() {
        Node node = new Node("");
        return generateTree(node, 0);
    }

    private static Node generateTree(Node node, int level) {
        // 2^8 = 256
        if (level == 9) {
            return null;
        }
        Node leftNode = new Node(".");
        Node rightNode = new Node("-");
        node.left = generateTree(leftNode, level + 1);
        node.right = generateTree(rightNode, level + 1);

        return node;
    }

    private static void generateCodewords(int numberOfCodewords, String initialCodeword, List<String> codewords,
                                          Node node, StringBuilder currentCodeword) {
        if (codewords.size() == numberOfCodewords) {
            return;
        }
        currentCodeword.append(node.symbol);

        // Arrived on leaf
        if (node.left == null) {
            String codeword = currentCodeword.toString();
            if (!initialCodeword.startsWith(codeword)
                    && !codeword.startsWith(initialCodeword)) {
                codewords.add(codeword);
            }
            return;
        }

        generateCodewords(numberOfCodewords, initialCodeword, codewords, node.left, currentCodeword);
        if (currentCodeword.length() > 0) {
            currentCodeword.deleteCharAt(currentCodeword.length() - 1);
        }
        generateCodewords(numberOfCodewords, initialCodeword, codewords, node.right, currentCodeword);
        if (currentCodeword.length() > 0) {
            currentCodeword.deleteCharAt(currentCodeword.length() - 1);
        }
    }

    private static void test() {
        List<String> codewords1 = generateCodewords(3, ".-.");
        System.out.println("Codewords 1");
        for (String codeword : codewords1) {
            System.out.println(codeword);
        }
        System.out.println();

        List<String> codewords2 = generateCodewords(4, "-");
        System.out.println("Codewords 2");
        for (String codeword : codewords2) {
            System.out.println(codeword);
        }
        System.out.println();

        List<String> codewords3 = generateCodewords(3, "..");
        System.out.println("Codewords 3");
        for (String codeword : codewords3) {
            System.out.println(codeword);
        }
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String data = lines.get(i++);
            int numberOfCodewords = Integer.parseInt(data);
            String initialCodeword = lines.get(i);

            List<String> codewords = generateCodewords(numberOfCodewords, initialCodeword);
            output.add("Case #" + caseId + ":");
            output.addAll(codewords);
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
