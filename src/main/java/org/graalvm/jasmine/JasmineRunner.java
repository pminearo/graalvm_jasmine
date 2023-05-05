package org.graalvm.jasmine;

import java.io.File;
import java.net.URL;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JasmineRunner {

    static final String JASMINE = "jasmine.js";
    static final String JASMINE_BOOT_0 = "boot0.js";
    static final String JASMINE_BOOT_1 = "boot1.js";
    static final String TEST_FILE = "IntegrationTest.js";

    private static final String JS = "js";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Context context;

    private final Value globalBindings;

    private Value jasmineEnv;

    public JasmineRunner() {

        context = Context.newBuilder().allowAllAccess(true).build();
        globalBindings = context.getBindings(JS);
    }

    public void initializeJasmine() throws Exception {

        logger.info("Setting up Jasmine Environment");

        // jasmine.js wants a global property set to "this"
        createGlobalVariables();

        // this sets up the jasmine environment which becomes globally available as "jasmineEnv"
        loadJasmine();
        loadJasmineBoot0();
        loadJasmineBoot1();

        final Value jasmine = globalBindings.getMember("jasmine");
        jasmineEnv = jasmine.getMember("getEnv").execute();

        logger.info("Jasmine Environment Set Up Complete");
    }

    public void run() throws Exception {

        logger.info("Executing Test");

        loadTest();

        jasmineEnv.getMember("execute").execute();

        logger.info("Done Executing Test");
    }

    Value getGlobalBindings() {
        return globalBindings;
    }

    void loadJasmine() throws Exception {

        logger.info("Loading Jasmine Require");
        logger.info("Looking for File: {}", JASMINE);

        loadScriptFile(JASMINE);

        logger.info("Loaded File");
    }

    void loadJasmineBoot0() throws Exception {

        logger.info("Loading Jasmine Boot");
        logger.info("Looking for File: {}", JASMINE_BOOT_0);

        loadScriptFile(JASMINE_BOOT_0);

        logger.info("Loaded File");
    }

    void loadJasmineBoot1() throws Exception {

        logger.info("Loading Jasmine Boot");
        logger.info("Looking for File: {}", JASMINE_BOOT_1);

        loadScriptFile(JASMINE_BOOT_1);

        logger.info("Loaded File");
    }

    void loadTest() throws Exception {

        logger.info("Loading Test");
        logger.info("Looking for Test File: {}", TEST_FILE);

        loadScriptFile(TEST_FILE);

        logger.info("Loaded Test File");
    }

    void loadScriptFile(final String scriptFile) throws Exception {

        logger.info("Loading File: {}", scriptFile);

        final File script = findFile(scriptFile);

        final Source source = Source.newBuilder(JS, script).build();

        context.eval(source);

        logger.info("Loaded File");
    }

    void createGlobalVariables() {
        logger.info("Creating Global Variables");

        final Value jasmineGlobalPrototype = globalBindings.getMember("Object");
        final Value jasmineGlobal = jasmineGlobalPrototype.newInstance();

        globalBindings.putMember("global", jasmineGlobal);

        logger.info("Finished Creating Global Variables");
    }

    File findFile(final String filePath) {

        URL fileUrl = getClass().getClassLoader().getResource(filePath);

        if (fileUrl == null) {
            throw new IllegalArgumentException("Could not find File on Classpath: " + filePath);
        }

        return new File(fileUrl.getPath());
    }

}
