package facebook.hacker.cup.year2021.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 28/08/21.
 */
public class ConsistencyChapter1 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2021/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "consistency_chapter_1_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "consistency_chapter_1_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "consistency_chapter_1_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "consistency_chapter_1_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "consistency_chapter_1_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "consistency_chapter_1_output.txt";

    public static long countChanges(String string) {
        long vowels = 0;
        long consonants = 0;

        Map<Character, Integer> vowelsMap = new HashMap<>();
        Map<Character, Integer> consonantsMap = new HashMap<>();

        Set<Character> vowelsSet = new HashSet<>();
        Set<Character> consonantsSet = new HashSet<>();

        int highestVowelFrequency = 0;
        int highestConsonantFrequency = 0;

        for (char character : string.toCharArray()) {
            if (isVowel(character)) {
                vowels++;
                int frequency = vowelsMap.getOrDefault(character, 0) + 1;
                vowelsMap.put(character, frequency);

                highestVowelFrequency = Math.max(highestVowelFrequency, frequency);
                vowelsSet.add(character);
            } else {
                consonants++;
                int frequency = consonantsMap.getOrDefault(character, 0) + 1;
                consonantsMap.put(character, frequency);

                highestConsonantFrequency = Math.max(highestConsonantFrequency, frequency);
                consonantsSet.add(character);
            }
        }

        if ((vowelsSet.isEmpty() && consonantsSet.size() == 1)
                || (consonantsSet.isEmpty() && vowelsSet.size() == 1)) {
            return 0;
        }

        long changesToVowels = consonants;
        if (vowelsSet.size() > 1) {
            changesToVowels += (vowels - highestVowelFrequency) * 2;
        }
        long changesToConsonants = vowels;
        if (consonantsSet.size() > 1) {
            changesToConsonants += (consonants - highestConsonantFrequency) * 2;
        }
        return Math.min(changesToVowels, changesToConsonants);
    }

    private static boolean isVowel(char character) {
        return character == 'A' ||
                character == 'E' ||
                character == 'I' ||
                character == 'O' ||
                character == 'U';
    }

    public static void main(String[] args) {
//        test();
        compete();
    }

    private static void test() {
        long seconds1 = countChanges("ABC");
        System.out.println("Seconds 1: " + seconds1 + " Expected: 2");

        long seconds2 = countChanges("F");
        System.out.println("Seconds 2: " + seconds2 + " Expected: 0");

        long seconds3 = countChanges("BANANA");
        System.out.println("Seconds 3: " + seconds3 + " Expected: 3");

        long seconds4 = countChanges("FBHC");
        System.out.println("Seconds 4: " + seconds4 + " Expected: 4");

        long seconds5 = countChanges("FOXEN");
        System.out.println("Seconds 5: " + seconds5 + " Expected: 5");

        long seconds6 = countChanges("CONSISTENCY");
        System.out.println("Seconds 6: " + seconds6 + " Expected: 12");

        long seconds7 = countChanges("HAAACKEEERCUUUP");
        System.out.println("Seconds 7: " + seconds7 + " Expected: 17");

        long seconds8 = countChanges("UFOVKZEOUTPUJSWEITEUAIYDRIBGUIUIOEII");
        System.out.println("Seconds 8: " + seconds8 + " Expected: 43");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String string = lines.get(i);

            long seconds = countChanges(string);
            output.add("Case #" + caseId + ": " + seconds);
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
