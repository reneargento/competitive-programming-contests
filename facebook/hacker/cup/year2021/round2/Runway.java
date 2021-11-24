package facebook.hacker.cup.year2021.round2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 11/09/21.
 */
@SuppressWarnings("unchecked")
public class Runway {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2021/Round 2/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "runway_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "runway_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "runway_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "runway_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "runway_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "runway_output.txt";

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static class ModelData {
        int modelsUsingStyle;
        int modelsUsingStyleThatNeverChanged;

        public ModelData(int modelsUsingStyle, int modelsUsingStyleThatNeverChanged) {
            this.modelsUsingStyle = modelsUsingStyle;
            this.modelsUsingStyleThatNeverChanged = modelsUsingStyleThatNeverChanged;
        }
    }

    private static long countHelpTimes(int[] clothes, int[][] requiredClothes) {
        Map<Integer, ModelData> modelDataMap = createModelDataMap(clothes);
        long helpTimes = 0;

        for (int[] clothesInRound : requiredClothes) {
            Map<Integer, Integer> stylePerQuantity = new HashMap<>();

            for (int clothe : clothesInRound) {
                int frequency = stylePerQuantity.getOrDefault(clothe, 0);
                stylePerQuantity.put(clothe, frequency + 1);
            }

            for (int clothesNeeded : stylePerQuantity.keySet()) {
                int frequencyNeeded = stylePerQuantity.get(clothesNeeded);

                for (int i = 0; i < frequencyNeeded; i++) {
                    if (modelDataMap.containsKey(clothesNeeded)) {
                        ModelData modelData = modelDataMap.get(clothesNeeded);
                        if (modelData.modelsUsingStyle < frequencyNeeded) {
                            if (!findAndDecrementFirstChange(modelDataMap, stylePerQuantity)) {
                                helpTimes++;
                                changeModel(modelDataMap, stylePerQuantity);
                            }
                            modelDataMap.get(clothesNeeded).modelsUsingStyle++;
                        }
                    } else {
                        if (!findAndDecrementFirstChange(modelDataMap, stylePerQuantity)) {
                            helpTimes++;
                            changeModel(modelDataMap, stylePerQuantity);
                        }
                        modelDataMap.put(clothesNeeded, new ModelData(1, 0));
                    }
                }
            }
        }
        return helpTimes;
    }

    private static boolean findAndDecrementFirstChange(Map<Integer, ModelData> modelDataMap,
                                                       Map<Integer, Integer> stylePerQuantity) {
        for (int clothe : modelDataMap.keySet()) {
            ModelData modelData = modelDataMap.get(clothe);
            if (modelData.modelsUsingStyleThatNeverChanged == 0) {
                continue;
            }

            if (!stylePerQuantity.containsKey(clothe)) {
                modelData.modelsUsingStyle--;
                modelData.modelsUsingStyleThatNeverChanged--;
                if (modelData.modelsUsingStyle == 0) {
                    modelDataMap.remove(clothe);
                }
                return true;
            } else {
                int frequencyNeeded = stylePerQuantity.get(clothe);
                if (frequencyNeeded < modelData.modelsUsingStyle) {
                    modelData.modelsUsingStyle--;
                    modelData.modelsUsingStyleThatNeverChanged--;
                    if (modelData.modelsUsingStyle == 0) {
                        modelDataMap.remove(clothe);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private static Map<Integer, ModelData> createModelDataMap(int[] clothes) {
        Map<Integer, ModelData> modelDataMap = new HashMap<>();

        for (int clothe : clothes) {
            ModelData modelData = modelDataMap.getOrDefault(clothe, new ModelData(0, 0));
            modelData.modelsUsingStyle++;
            modelData.modelsUsingStyleThatNeverChanged++;
            modelDataMap.put(clothe, modelData);
        }
        return modelDataMap;
    }

    private static void changeModel(Map<Integer, ModelData> modelDataMap,
                                    Map<Integer, Integer> stylePerQuantity) {
        for (int clothe : modelDataMap.keySet()) {
            ModelData modelData = modelDataMap.get(clothe);
            if (!stylePerQuantity.containsKey(clothe)) {
                modelData.modelsUsingStyle--;
                if (modelData.modelsUsingStyle == 0) {
                    modelDataMap.remove(clothe);
                }
                return;
            } else {
                int frequencyNeeded = stylePerQuantity.get(clothe);
                if (frequencyNeeded < modelData.modelsUsingStyle) {
                    modelData.modelsUsingStyle--;
                    if (modelData.modelsUsingStyle == 0) {
                        modelDataMap.remove(clothe);
                    }
                    return;
                }
            }
        }
    }

    private static void test() {
        int[] currentClothes1 = new int[] { 1 };
        int[][] requiredClothes1 = { { 2 }, { 2 }, { 1 } };
        long helpTimes1 = countHelpTimes(currentClothes1, requiredClothes1);
        System.out.println("Help times 1: " + helpTimes1 + " Expected: 1");

        int[] currentClothes2 = new int[] { 1, 11 };
        int[][] requiredClothes2 = { { 11, 1 }, { 1, 11 }, { 11, 1 }, { 1, 11 } };
        long helpTimes2 = countHelpTimes(currentClothes2, requiredClothes2);
        System.out.println("Help times 2: " + helpTimes2 + " Expected: 0");

        int[] currentClothes3 = new int[] { 1, 11 };
        int[][] requiredClothes3 = { { 2, 12 }, { 3, 13 }, { 4, 14 }, { 5, 15 } };
        long helpTimes3 = countHelpTimes(currentClothes3, requiredClothes3);
        System.out.println("Help times 3: " + helpTimes3 + " Expected: 6");

        int[] currentClothes4 = new int[] { 10, 20, 20 };
        int[][] requiredClothes4 = { { 20, 10, 10 }, { 30, 30, 20 } };
        long helpTimes4 = countHelpTimes(currentClothes4, requiredClothes4);
        System.out.println("Help times 4: " + helpTimes4 + " Expected: 1");

        int[] currentClothes5 = new int[] { 1, 4, 4, 10, 1 };
        int[][] requiredClothes5 = { { 4, 8, 1, 7, 5 },
                                    { 10, 1, 7, 3, 6 },
                                    { 7, 7, 3, 1, 1 },
                                    { 2, 3, 1, 8, 5 }
        };
        long helpTimes5 = countHelpTimes(currentClothes5, requiredClothes5);
        System.out.println("Help times 5: " + helpTimes5 + " Expected: 6");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i++).split(" ");
            int rounds = Integer.parseInt(data[0]);
            int models = Integer.parseInt(data[1]);

            int[] currentClothes = new int[models];
            String[] modelsCurrentClothes = lines.get(i++).split(" ");
            for (int m = 0; m < modelsCurrentClothes.length; m++) {
                currentClothes[m] = Integer.parseInt(modelsCurrentClothes[m]);
            }

            int[][] requiredClothes = new int[rounds][models];
            for (int r = 0; r < rounds; r++) {
                String[] roundClothes = lines.get(i).split(" ");
                for (int c = 0; c < roundClothes.length; c++) {
                    String roundClothe = roundClothes[c];
                    requiredClothes[r][c] = Integer.parseInt(roundClothe);
                }

                if (r != rounds - 1) {
                    i++;
                }
            }
            long helpTimes = countHelpTimes(currentClothes, requiredClothes);
            output.add("Case #" + caseId + ": " + helpTimes);
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
