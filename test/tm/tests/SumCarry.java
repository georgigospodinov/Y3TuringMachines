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

class SumCarry {

    private static final String TM_NAME = "sum";
    private static final String TM = TM_DIR + TM_NAME + TM_EXTENSION;
    private static final String SUB_DIR = "/carry";
    private static final Logger logger = new Logger("logs/SumCarry.log");

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
    void trivial() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/trivial" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void easy() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/easy" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void chainW1() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/chain_w1" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void chainW2() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/chain_w2" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void superChain() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/super_chain" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void mixed() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/mixed" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void mixedShort() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/mixed_short" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void incorrect() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/incorrect" + INPUT_EXTENSION;
        print(in);
        assertFalse(utm.belongsToLanguage(in));
    }

    @Test
    void longNumbers() {
        String in = INPUT_DIR + TM_NAME + SUB_DIR + "/long" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

}
