package tm;

import static tm.UniversalTuringMachine.*;

public class Transition {

    // Transition parsing from input line.
    private static final int STATE1_INDEX = 0;
    private static final int INPUT_INDEX = 1;
    private static final int STATE2_INDEX = 2;
    private static final int OUTPUT_INDEX = 3;
    private static final int MOVE_INDEX = 4;
    private static final int NUMBER_OF_VALUES = 5;

    private static String parseMove(String move) {
        switch (move) {
            case LEFT:
                return LEFT;
            case RIGHT:
                return RIGHT;
            case STILL:
                return STILL;
            default:
                throw new IllegalArgumentException("Move direction not recognized! " + move);
        }
    }

    static void createTransition(String line, UniversalTuringMachine tm) {
        String[] vals = line.trim().split(VALUES_SEPARATOR);
        if (vals.length != NUMBER_OF_VALUES)
            throw new IllegalArgumentException("Incorrect transition definition: \"" + line + "\"!");

        State s1 = tm.getState(vals[STATE1_INDEX]);
        State s2 = tm.getState(vals[STATE2_INDEX]);
        char input = tm.alphabet.validate(vals[INPUT_INDEX]);
        char output = tm.alphabet.validate(vals[OUTPUT_INDEX]);
        String move = parseMove(vals[MOVE_INDEX]);
        Transition t = new Transition(s2, output, move);
        s1.addTransition(input, t);
    }

    private final State nextState;
    private final char output;
    private final String move;

    private Transition(State nextState, char output, String move) {
        this.nextState = nextState;
        this.output = output;
        this.move = move;
    }

    State getNextState() {
        return nextState;
    }

    char getOutput() {
        return output;
    }

    String getMove() {
        return move;
    }

    @Override
    public String toString() {
        return "{output='" + output + "', move='" + move + "'}";
    }
}
