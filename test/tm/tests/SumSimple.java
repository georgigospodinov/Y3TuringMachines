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

class SumSimple {

    private static final String TM_NAME = "sum";
    private static final String TM = TM_DIR + TM_NAME + TM_EXTENSION;
    private static final String SUB_DIR = "/simple";
    private static final Logger logger = new Logger("logs/SumSimple.log");

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
    void zeros() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/00" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void zeroOne() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/01" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void oneZero() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/10" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void incorrect() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/incorrect" + INPUT_EXTENSION;
        print(in);
        assertFalse(utm.belongsToLanguage(in));
    }

}
