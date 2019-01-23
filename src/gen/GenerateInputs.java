package gen;

import util.MakePalindrome;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static util.Constants.GEN_DIR;
import static util.Constants.INPUT_EXTENSION;
import static util.PrintFormatting.print;
import static util.ProgressBar.formatBar;

public class GenerateInputs {

    private static int startSize, endSize;
    private static String tmName;

    private static BufferedWriter openFile(String filename) throws Exception {
        File f = new File(filename);
        f.getParentFile().mkdirs();
        return new BufferedWriter(new FileWriter(filename));
    }

    private static void generateBasic(int size) throws Exception {
        String filename = GEN_DIR + tmName + "/a" + size + INPUT_EXTENSION;
        BufferedWriter writer = openFile(filename);

        for (int i = 0; i < size; i++)
            writer.write("a");
        writer.write("_");

        writer.close();
    }

    private static void generateBrackets(int size) throws Exception {
        String filename = GEN_DIR + tmName + "/n" + size + INPUT_EXTENSION;
        BufferedWriter writer = openFile(filename);
        if (size % 2 == 1) size--;  // Make size even

        writer.write("x");
        int i;
        for (i = 0; i < size / 2; i++)
            writer.write("(");
        if (size % 2 == 1)
            writer.write("_");
        for (i = 0; i < size / 2; i++)
            writer.write(")");
        writer.write("x");

        writer.close();
    }

    private static void generatePalindrome(int size) throws Exception {
        String filename = GEN_DIR + tmName + "/p" + size + INPUT_EXTENSION;
        BufferedWriter writer = openFile(filename);
        Random r = new Random();

        for (int i = 0; i < size / 2; i++) {
            int x = r.nextInt(3);
            writer.write(String.valueOf(x));
        }
        writer.close();

        MakePalindrome.main(new String[]{filename});
    }

    private static void generateSort(int size) throws Exception {
        String filename = GEN_DIR + tmName + "/r" + size + INPUT_EXTENSION;
        BufferedWriter writer = openFile(filename);
        writer.write("_");

        int i;
        for (i = 0; i < 1 + size / 2; i++)
            writer.write("2");
        for (; i < size; i++)
            writer.write("0");
        writer.write("_");

        writer.close();
    }

    private static String randomBinary(int wordSize) throws IOException {
        Random r = new Random();
        StringBuilder word = new StringBuilder("");

        // Numbers should begin with 1.
        word.append("1");
        for (int i = 1; i < wordSize; i++) {
            int x = r.nextInt(2);
            word.append(String.valueOf(x));
        }
        return word.reverse().toString();
    }

    private static final char ZERO = '0';
    private static final char ONE = '1';

    private static String addBinary(String w1, String w2) {
        StringBuilder w3 = new StringBuilder("");

        int len = Math.max(w1.length(), w2.length());
        boolean carryPresent = false;
        for (int i = 0; i < len; i++) {
            char v1 = i < w1.length() ? w1.charAt(i) : ZERO;
            char v2 = i < w2.length() ? w2.charAt(i) : ZERO;
            char v3;
            // 0 + 0 is 0 and maybe carry. Reset carry.
            if (v1 == ZERO && v2 == ZERO) {
                v3 = carryPresent ? ONE : ZERO;
                carryPresent = false;
            }
            // 1 + 1 is 0 and maybe carry. Set carry.
            else if (v1 == ONE && v2 == ONE) {
                v3 = carryPresent ? ONE : ZERO;
                carryPresent = true;
            }
            // 0 + 1 or 1 + 0 is 1
            else v3 = carryPresent ? ZERO : ONE;

            w3.append(String.valueOf(v3));
        }

        if (carryPresent)
            w3.append(String.valueOf(ONE));

        return w3.toString();
    }

    private static void generateSum(int size) throws Exception {
        String filename = GEN_DIR + tmName + "/w" + size + INPUT_EXTENSION;
        BufferedWriter writer = openFile(filename);

        int wSize = size / 3;
        String w1 = randomBinary(wSize);
        String w2 = randomBinary(wSize);
        String w3 = addBinary(w1, w2);

        writer.write(w1);
        writer.write("#");
        writer.write(w2);
        writer.write("#");
        writer.write(w3);

        writer.close();
    }

    private static void setSizes() {
        print("Enter start size and end size:");
        Scanner sc = new Scanner(System.in);
        startSize = sc.nextInt();
        endSize = sc.nextInt();
    }

    public static void main(String[] args) throws Exception {
        setSizes();
        for (String filename : args) {
            tmName = filename;
            print(tmName);
            System.out.print(formatBar(0, endSize - startSize + 1));
            size_loop:
            for (int size = startSize; size <= endSize; size++) {
                switch (tmName) {
                    case "basic":
                        generateBasic(size);
                        break;
                    case "brackets":
                        generateBrackets(size);
                        break;
                    case "palindrome":
                        generatePalindrome(size);
                        break;
                    case "sort":
                        generateSort(size);
                        break;
                    case "sum":
                        generateSum(size);
                        break;
                    default:
                        print("could not match", tmName);
                        break size_loop;
                }
                System.out.print(formatBar(size - startSize + 1, endSize - startSize + 1));
            }
            print("\n");
        }
    }

}
