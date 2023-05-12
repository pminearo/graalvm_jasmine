package org.graalvm.jasmine;

import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Test;

public class JasmineRunnerTest {

    private static final String TEST_FILE = "IntegrationTest.js";


    @Test
    public void testRun() {

        final JasmineRunner scriptRunner = new JasmineRunner();

        scriptRunner.run(TEST_FILE);
        scriptRunner.displayReport();
    }

}
