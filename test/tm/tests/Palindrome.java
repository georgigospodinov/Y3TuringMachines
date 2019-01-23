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

class Palindrome {

    private static final String TM_NAME = "palindrome";
    private static final String TM = TM_DIR + TM_NAME + TM_EXTENSION;
    private static final Logger logger = new Logger("logs/Palindrome.log");

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
    void zerosOnly() {
        String in = INPUT_DIR + TM_NAME + "/0_only" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void onesOnly() {
        String in = INPUT_DIR + TM_NAME + "/1_only" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void twosOnly() {
        String in = INPUT_DIR + TM_NAME + "/2_only" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void onesSurroundedByZerosOnly() {
        String in = INPUT_DIR + TM_NAME + "/01_10" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void basicNesting() {
        String in = INPUT_DIR + TM_NAME + "/21012" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void almost() {
        String in = INPUT_DIR + TM_NAME + "/almost" + INPUT_EXTENSION;
        print(in);
        assertFalse(utm.belongsToLanguage(in));
    }

    @Test
    void totallyNot() {
        String in = INPUT_DIR + TM_NAME + "/totally_not" + INPUT_EXTENSION;
        print(in);
        assertFalse(utm.belongsToLanguage(in));
    }

    @Test
    void complex() {
        String in = INPUT_DIR + TM_NAME + "/complex" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    @Test
    void complexX3() {
        String in = INPUT_DIR + TM_NAME + "/complex_x3" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

    void superComplex() {
        String in = INPUT_DIR + TM_NAME + "/super_complex" + INPUT_EXTENSION;
        print(in);
        assertTrue(utm.belongsToLanguage(in));
    }

}
