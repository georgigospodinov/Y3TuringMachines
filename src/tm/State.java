package tm;

import util.Logger;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static tm.UniversalTuringMachine.VALUES_SEPARATOR;

class State {

    private static final int STATE_INDEX = 0;
    private static final int ACCEPTING_INDEX = 1;
    private static final String ACCEPTING_INDICATOR = "+";

    private static boolean parseAccepting(String[] values) {
        if (values.length == 1) return false;
        if (values.length == 2) return values[ACCEPTING_INDEX].equals(ACCEPTING_INDICATOR);
        else throw new IllegalArgumentException("Incorrect state definition: " + Arrays.toString(values));
    }

    static State createState(String line, LinkedHashMap<String, State> states, Logger logger) {
        String[] vals = line.trim().split(VALUES_SEPARATOR);
        String id = vals[STATE_INDEX];
        if (states.containsKey(id)) {
            String errorMsg = "States cannot be redefined! See log for further details";
            logger.log("Existing " + states.get(id).toString());
            throw new IllegalArgumentException(errorMsg);
        }
        boolean accepting = parseAccepting(vals);
        State s = new State(accepting);
        states.put(id, s);
        return s;
    }

    // Map input to transition.
    private final LinkedHashMap<Character, Transition> transitions = new LinkedHashMap<>();
    private final boolean accepting;

    private State(boolean accepting) {
        this.accepting = accepting;
    }

    boolean isAccepting() {
        return accepting;
    }

    Transition getTransition(char in) {
        return transitions.get(in);
    }

    void addTransition(char in, Transition t) {
        transitions.put(in, t);
    }
}
