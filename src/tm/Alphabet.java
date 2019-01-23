package tm;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static tm.UniversalTuringMachine.VALUES_SEPARATOR;

class Alphabet {
    private final LinkedHashSet<Character> ALLOWED_CHARACTERS;
    private static final Character[] DEFAULT_ALLOWED = {'_'};
    private static final String ALPHABET = "alphabet";

    static Alphabet parseAllowed(String line) {
        String[] values = line.split(VALUES_SEPARATOR);
        if (!values[0].equals(ALPHABET))
            throw new IllegalArgumentException("Incorrectly formatted alphabet: " + line);

        int numberOfAllowedInputs = Integer.parseInt(values[1]);
        if (numberOfAllowedInputs + 2 != values.length)
            throw new IllegalArgumentException("Incorrect number of allowed: " + line);

        Alphabet alphabet = new Alphabet();
        for (int i = 0; i < numberOfAllowedInputs; i++)
            alphabet.addAllowed(values[i + 2]);

        return alphabet;
    }

    private Alphabet() {
        ALLOWED_CHARACTERS = new LinkedHashSet<>(Arrays.asList(DEFAULT_ALLOWED));
    }

    private static char toChar(String s) {
        if (s.length() != 1)
            throw new IllegalArgumentException("Expected single character, got " + s);

        return s.charAt(0);
    }

    private void addAllowed(String s) {
        ALLOWED_CHARACTERS.add(toChar(s));
    }

    char validate(String s) {
        char c = toChar(s);
        if (ALLOWED_CHARACTERS.contains(c)) return c;
        throw new IllegalArgumentException("Value " + c + " is not in the alphabet!");
    }

    @Override
    public String toString() {
        return "Alphabet{" + ALLOWED_CHARACTERS + "}";
    }
}
