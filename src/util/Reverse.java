package util;

import java.util.Scanner;

import static util.PrintFormatting.print;

public class Reverse {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        StringBuilder reverse = new StringBuilder(line);
        reverse.reverse();
        print(reverse.toString());
    }
}
