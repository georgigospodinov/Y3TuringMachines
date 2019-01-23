package tm.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tm.UniversalTuringMachine;
import util.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static tm.UniversalTuringMachine.parseTMDescription;
import static util.Constants.*;
import static util.PrintFormatting.print;

public class Brackets {

    private static final String TM_NAME = "brackets";
    private static final String TM = TM_DIR + TM_NAME + TM_EXTENSION;
    private static final Logger logger = new Logger("logs/Brackets.log");

    @AfterAll
    static void closeLog() {
        logger.close();
    }

    private UniversalTuringMachine utm;

    @BeforeEach
    void makeTuring() {
        utm = parseTMDescription(TM, logger);
        assertNotNull(utm);
    }

    @Test
    void simple() {
        String in = INPUT_DIR + TM_NAME + "/simple" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void doubleBrackets() {
        String in = INPUT_DIR + TM_NAME + "/double" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void heavy() {
        String in = INPUT_DIR + TM_NAME + "/heavy" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void mismatch() {
        String in = INPUT_DIR + TM_NAME + "/mismatch" + INPUT_EXTENSION;
        print(in);
        assertFalse(utm.belongsToLanguage(in));
    }

    @Test
    void noOpening() {
        String in = INPUT_DIR + TM_NAME + "/no_opening" + INPUT_EXTENSION;
        print(in);
        assertFalse(utm.belongsToLanguage(in));
    }

    @Test
    void noClosing() {
        String in = INPUT_DIR + TM_NAME + "/no_closing" + INPUT_EXTENSION;
        print(in);
        assertFalse(utm.belongsToLanguage(in));
    }

}
