package tm;

import util.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;

import static tm.Alphabet.parseAllowed;
import static tm.State.createState;
import static tm.Transition.createTransition;
import static util.Constants.INPUT_EXTENSION;
import static util.Constants.TM_EXTENSION;
import static util.PrintFormatting.print;

public class UniversalTuringMachine {

    // Move directions
    static final String LEFT = "L";
    static final String RIGHT = "R";
    static final String STILL = "S";

    static final String VALUES_SEPARATOR = " ";
    private static final String STATES = "states";
    private static final String TAPE_DIR = "tapes/";
    private static final String TAPE_EXTENSION = ".tape";

    private static final char EOF = '\u001a';
    private static final char ERROR_CHAR_CODE = (char) -1;
    private static final boolean ON_ERROR_RESPONSE = false;

    private String nextCodeLine(BufferedReader reader) {
        String line;
        do {
            try {
                line = reader.readLine();
            }
            catch (IOException e) {
                logger.log(e);
                return null;
            }
        }
        while (line.startsWith("//"));

        return line;
    }

    public static UniversalTuringMachine parseTMDescription(String tmDescriptionFile, Logger logger) {

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(tmDescriptionFile));
        }
        catch (FileNotFoundException e) {
            logger.log(e);
            return null;
        }

        File tmFile = new File(tmDescriptionFile);
        int extensionIndex = tmFile.getName().lastIndexOf(TM_EXTENSION);
        UniversalTuringMachine tm = new UniversalTuringMachine(tmFile.getName().substring(0, extensionIndex), logger);

        String line = tm.nextCodeLine(reader);
        if (line == null) return null;

        // First line defines number of states.
        String[] states = line.split(VALUES_SEPARATOR);

        if (!states[0].equals(STATES) || states.length != 2)
            throw new IllegalArgumentException("Incorrectly formatted first line: " + Arrays.toString(states));

        // States definitions.
        int numberOfStates = Integer.parseInt(states[1]);
        // The first defined state is the initial state.
        tm.initState = createState(tm.nextCodeLine(reader), tm.states, logger);

        for (int i = 1; i < numberOfStates; i++)
            createState(tm.nextCodeLine(reader), tm.states, logger);

        // Allowed characters
        tm.alphabet = parseAllowed(tm.nextCodeLine(reader));

        // Parse the rest of the transitions.
        try {
            while (reader.ready())
                createTransition(tm.nextCodeLine(reader), tm);
        }
        catch (IOException e) {
            logger.log(e);
        }

        return tm;
    }

    private final Logger logger;
    private final String tmName;
    private State initState, currentState;
    private RandomAccessFile tape;
    private long numberOfTransitions = 0, tapeLength;
    Alphabet alphabet;
    private final LinkedHashMap<String, State> states = new LinkedHashMap<>();

    private UniversalTuringMachine(String tmName, Logger logger) {
        this.tmName = tmName;
        this.logger = logger;
    }

    State getState(String id) {
        State s = states.get(id);
        if (s == null)
            throw new IllegalArgumentException("State " + id + " not recognized!");

        return s;
    }

    public long getTapeLength() {
        return tapeLength;
    }

    public long getNumberOfTransitions() {
        return numberOfTransitions;
    }

    public String getTmName() {
        return tmName;
    }

    private void resetNumberOfTransitions() {
        numberOfTransitions = 0;
    }

    private void createTape(String inputFilename) {
        //<editor-fold desc="Create tape file object.">
        File inputFile = new File(inputFilename);
        int extensionIndex = inputFile.getName().lastIndexOf(INPUT_EXTENSION);
        String tapeFilename = inputFile.getName().substring(0, extensionIndex) + TAPE_EXTENSION;
        File tapeFile = new File(TAPE_DIR + tmName + "/" + tapeFilename);
        //</editor-fold>

        //<editor-fold desc="Create tape file.">
        try {
            tapeFile.getParentFile().mkdirs();
            tapeFile.createNewFile();
        }
        catch (IOException e) {
            logger.log(e);
            return;
        }
        //</editor-fold>

        //<editor-fold desc="Create tape accessor and set length.">
        try {
            tape = new RandomAccessFile(tapeFile, "rw");
            tape.setLength(inputFile.length());
            tapeLength = tape.length();
        }
        catch (IOException e) {
            logger.log(e);
            return;
        }
        //</editor-fold>

        //<editor-fold desc="Copy input to tape and set pointer to 0.">
        try {
            FileReader inputReader = new FileReader(inputFile);
            while (inputReader.ready()) tape.write(inputReader.read());
            tape.seek(0);
        }
        catch (IOException e) {
            logger.log(e);
        }
        //</editor-fold>
    }

    private void closeTape() {
        try {
            tape.close();
        }
        catch (IOException e) {
            logger.log(e);
        }
    }

    private boolean atOrRightAfterLastSymbol() {
        try {
            return tape.getFilePointer() >= tape.length() - 2;  // Minus 2 because of EOF
        }
        catch (IOException e) {
            logger.log(e);
            return ON_ERROR_RESPONSE;
        }
    }

    private char readCurrent() {
        try {
            char c = (char) tape.readByte();
            tape.seek(tape.getFilePointer() - 1);
            return c;
        }
        catch (EOFException e) {
            return EOF;
        }
        catch (IOException e) {
            logger.log(e);
            return ERROR_CHAR_CODE;
        }
    }

    private void writeCurrent(char output) {
        try {
            tape.writeByte(output);
            tape.seek(tape.getFilePointer() - 1);
        }
        catch (IOException e) {
            logger.log(e);
        }
    }

    private void positionTapeReader(String move) {
        try {
            long fp = tape.getFilePointer();
            switch (move) {
                case LEFT:
                    if (fp > 0)
                        tape.seek(fp - 1);
                    break;
                case RIGHT:
                    if (fp - 1 < tape.length())
                        tape.skipBytes(1);
                case STILL:
            }
        }
        catch (IOException e) {
            logger.log(e);
        }
    }

    private boolean evaluateTape() {
        boolean lastSymbolProcessed = false;
        char c = readCurrent();
        switch (c) {
            case EOF:
                return currentState.isAccepting();
            case ERROR_CHAR_CODE:
                return ON_ERROR_RESPONSE;
        }

        while (true) {
            Transition t = currentState.getTransition(c);
            // No transition for input c.
            if (t == null)
                return lastSymbolProcessed && currentState.isAccepting();

            numberOfTransitions++;
            writeCurrent(t.getOutput());
            currentState = t.getNextState();

            if (atOrRightAfterLastSymbol())
                lastSymbolProcessed = true;

            positionTapeReader(t.getMove());
            c = readCurrent();
            switch (c) {
                case EOF:
                    return currentState.isAccepting();
                case ERROR_CHAR_CODE:
                    return ON_ERROR_RESPONSE;
            }
        }
    }

    public boolean belongsToLanguage(String inputFilename) {
        createTape(inputFilename);
        resetNumberOfTransitions();
        currentState = initState;
        boolean b = evaluateTape();
        closeTape();
        return b;
    }

    @Override
    public String toString() {
        Set<String> keys = states.keySet();
        String id = "--";
        for (String k : keys)
            if (states.get(k).equals(currentState)) {
                id = k;
                break;
            }
        return "{currentState=" + id +
                ", numberOfTransitions=" + numberOfTransitions +
                ", alphabet=" + alphabet +
                ", states=" + states +
                "}";
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            print("Usage: java tm.UniversalTuringMachine TM_descriptor input_text_file [further_input_files]");
            return;
        }
        Logger logger = new Logger("logs/log.log");
        UniversalTuringMachine utm = parseTMDescription(args[0], logger);
        if (utm == null) {
            print("Could not simulate turing machine.");
            logger.close();
            return;
        }

        for (int i = 1; i < args.length; i++)
            print(args[i] + " is accepted? " + utm.belongsToLanguage(args[i]));
        logger.close();
    }
}
