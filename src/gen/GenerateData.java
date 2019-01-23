package gen;

import tm.UniversalTuringMachine;
import util.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;
import java.util.Set;

import static tm.UniversalTuringMachine.parseTMDescription;
import static util.Constants.*;
import static util.PrintFormatting.*;
import static util.ProgressBar.formatBar;

public class GenerateData {

    /**
     * Map Turing Machine to data points.
     * Data points are a map of input length to number of transitions.
     */
    private static final LinkedHashMap<String, LinkedHashMap<Long, Long>> DATA_ARRAYS = new LinkedHashMap<>();

    private static long getCode(String filepath) {
        File file = new File(filepath);
        String filename = file.getName();
        int indexOfExtension = filename.lastIndexOf(INPUT_EXTENSION);
        return Long.parseLong(filename.substring(1, indexOfExtension));
    }

    private static Logger logger;

    private static void simulateNext(String tmName, PriorityQueue<String> paths) {
        UniversalTuringMachine utm = parseTMDescription(tmName, logger);
        if (utm == null) {
            print("Could not simulate turing machine " + tmName);
            return;
        }
        print(tmName);

        DATA_ARRAYS.put(utm.getTmName(), new LinkedHashMap<>());
        LinkedHashMap<Long, Long> dataPoints = DATA_ARRAYS.get(utm.getTmName());
        int originalSize = paths.size(), workDone;
        while (!paths.isEmpty()) {
            workDone = originalSize - paths.size();
            System.out.print(formatBar(workDone, originalSize));

            String path = paths.poll();
            if (!utm.belongsToLanguage(path))
                print("\nerror on " + path);
            dataPoints.put(getCode(path), utm.getNumberOfTransitions());
        }

        workDone = originalSize - paths.size();
        print(formatBar(workDone, originalSize), "\n");
    }

    private static void runTM(File tmInFilesDir, File[] inFiles) {
        // The directory name is the name of the tm.
        String tmName = TM_DIR + tmInFilesDir.getName() + TM_EXTENSION;

        // The files inside the directory need to be passed to the simulateNext method
        PriorityQueue<String> fullPaths = new PriorityQueue<>();
        for (File inFile : inFiles)
            fullPaths.add(inFile.getAbsolutePath());

        simulateNext(tmName, fullPaths);
    }

    private static void writeData(String tmName, LinkedHashMap<Long, Long> dataPoints) {
        File dataDir = new File(DATA_DIR);
        dataDir.mkdir();
        File csv = new File(dataDir, tmName + DATA_SUFFIX);

        print(tmName);
        long minSize = Long.MAX_VALUE, maxSize = Long.MIN_VALUE;
        Set<Long> keys = dataPoints.keySet();
        for (long key : keys) {

            if (key < minSize)
                minSize = key;

            if (key > maxSize)
                maxSize = key;
        }
        int totalWork = (int) (maxSize - minSize);
        System.out.print(formatBar(0, totalWork));
        try {
            csv.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(csv));
            writer.write("size" + SEPARATOR + "transitions" + NEW_LINE);

            long finalMinSize = minSize;
            dataPoints.forEach((size, transitions) -> {
                System.out.print(formatBar((int) (size - finalMinSize), totalWork));
                try {
                    writer.write(size + SEPARATOR + transitions + NEW_LINE);
                }
                catch (IOException e) {
                    logger.log(e);
                }
            });
            print("\n");

            writer.close();
        }
        catch (IOException e) {
            logger.log(e);
        }
    }

    public static void main(String[] args) {
        logger = new Logger("logs/GenData.log");
        File genDir = new File(GEN_DIR);

        print("Running TMs.");
        // These are the directories that contain the input files for each tm respectively.
        File[] tmInFilesDirs = genDir.listFiles();
        if (tmInFilesDirs != null) {
            // Iterate the directories
            for (File tmInFilesDir : tmInFilesDirs) {
                File[] inFiles = tmInFilesDir.listFiles();
                if (inFiles != null)
                    runTM(tmInFilesDir, inFiles);
            }
        }

        print("Writing data to files.");
        DATA_ARRAYS.forEach(GenerateData::writeData);

        logger.close();
    }

}
