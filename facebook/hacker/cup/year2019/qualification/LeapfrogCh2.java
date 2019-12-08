package facebook.hacker.cup.year2019.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 15/06/19.
 */
// https://www.facebook.com/hackercup/problem/2426282194266338/
public class LeapfrogCh2 {
    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2019/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "leap_frog_2_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "leap_frog_2_sample_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "leap_frog_2_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "leap_frog_2_output.txt";

    public static void main(String[] args) {
        //test();
        compete();
    }

    private static void test() {
        String lilypads1 = "A.";
        System.out.println(canLeap(lilypads1) + " Expected: N");

        String lilypads2 = "AB.";
        System.out.println(canLeap(lilypads2) + " Expected: Y");

        String lilypads3 = "ABB";
        System.out.println(canLeap(lilypads3) + " Expected: N");

        String lilypads4 = "A.BB";
        System.out.println(canLeap(lilypads4) + " Expected: Y");

        String lilypads5 = "A..BB..B";
        System.out.println(canLeap(lilypads5) + " Expected: Y");

        String lilypads6 = "A.B..BBB.";
        System.out.println(canLeap(lilypads6) + " Expected: Y");

        String lilypads7 = "AB.........";
        System.out.println(canLeap(lilypads7) + " Expected: N");

        String lilypads8 = "A.B..BBBB.BB";
        System.out.println(canLeap(lilypads8) + " Expected: Y");

        String lilypads9 = "AB.B.B...";
        System.out.println(canLeap(lilypads9) + " Expected: Y");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String lilypads = lines.get(i);
            String canLeap = canLeap(lilypads);

            output.add("Case #" + caseId + ": " + canLeap);
            caseId++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static String canLeap(String lilypads) {
        int betaFrogs = 0;
        int unoccupied = 0;
        boolean canLeap;

        for (int i = 0; i < lilypads.length(); i++) {
            if (lilypads.charAt(i) == 'B') {
                betaFrogs++;
            } else if (lilypads.charAt(i) == '.') {
                unoccupied++;
            }
        }

        if (unoccupied == 0) {
            canLeap = false;
        } else canLeap = unoccupied <= betaFrogs || betaFrogs >= 2;

        if (canLeap) {
            return "Y";
        }
        return "N";
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

