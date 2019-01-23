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

class Div5 {

    private static final String TM_NAME = "div5";
    private static final String TM = TM_DIR + TM_NAME + TM_EXTENSION;
    private static final Logger logger = new Logger("logs/Div5.log");

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
    void five() {
        String in = INPUT_DIR + TM_NAME + "/five" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void ten() {
        String in = INPUT_DIR + TM_NAME + "/ten" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void fifty() {
        String in = INPUT_DIR + TM_NAME + "/fifty" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    //    @Test
    void big() {
        String in = INPUT_DIR + TM_NAME + "/big" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void twentyFive() {
        String in = INPUT_DIR + TM_NAME + "/twenty_five" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void not() {
        String in = INPUT_DIR + TM_NAME + "/not" + INPUT_EXTENSION;
        print(in);
        assertFalse(utm.belongsToLanguage(in));
    }

}
