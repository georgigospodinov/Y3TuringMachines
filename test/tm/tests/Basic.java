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

class Basic {

    private static final String TM_NAME = "basic";
    private static final String TM = TM_DIR + TM_NAME + TM_EXTENSION;
    private static final Logger logger = new Logger("logs/Basic.log");

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
    void aOnly() {
        String in = INPUT_DIR + TM_NAME + "/a_only" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void bOnly() {
        String in = INPUT_DIR + TM_NAME + "/b_only" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void ab() {
        String in = INPUT_DIR + TM_NAME + "/a_b" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void ba() {
        String in = INPUT_DIR + TM_NAME + "/b_a" + INPUT_EXTENSION;
        print(in);
        assertFalse(utm.belongsToLanguage(in));
    }

    @Test
    void invalidChars() {
        String in = INPUT_DIR + TM_NAME + "/invalid_chars" + INPUT_EXTENSION;
        print(in);
        assertFalse(utm.belongsToLanguage(in));
    }

    @Test
    void tooManySpaces() {
        String in = INPUT_DIR + TM_NAME + "/too_many_spaces" + INPUT_EXTENSION;
        print(in);
        assertFalse(utm.belongsToLanguage(in));
    }

}
