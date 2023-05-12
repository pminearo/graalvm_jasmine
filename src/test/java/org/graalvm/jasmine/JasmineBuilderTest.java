package org.graalvm.jasmine;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Test;

public class JasmineBuilderTest {

    private static final String TEST_FILE = "IntegrationTest.js";

    private static final String TEST_FILE_1 = "IntegrationTest1.js";

    private final Context context = Context.newBuilder().allowAllAccess(true).build();
    private final JasmineBuilder jasmineBuilder = new JasmineBuilder();



    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testLoadScript() throws Exception {

        final URL fileUrl = getClass().getClassLoader().getResource(TEST_FILE_1);

        if (fileUrl == null) {
            fail("Could not find file: " + TEST_FILE_1);
        }

        final File testFile = new File(fileUrl.getPath());
        final String test = FileUtils.readFileToString(testFile, StandardCharsets.UTF_8);

        jasmineBuilder.initializeJasmine(context);
        jasmineBuilder.loadScript(context, TEST_FILE_1, test);

        validateGlobalVariables();
    }

    @Test
    public void testLoadTestFiles() {

        final List<String> testFiles = new ArrayList<>();
        testFiles.add(TEST_FILE);
        testFiles.add(TEST_FILE_1);

        jasmineBuilder.initializeJasmine(context);
        jasmineBuilder.loadTestFiles(context, testFiles);

        validateGlobalVariables();
    }

    @Test
    public void testLoadTestFile() {

        jasmineBuilder.initializeJasmine(context);
        jasmineBuilder.loadTestFile(context, TEST_FILE);

        validateGlobalVariables();
    }

    @Test
    public void testInitializeJasmine() {

        jasmineBuilder.initializeJasmine(context);

        validateGlobalVariables();
    }

    @Test
    public void testLoadJasmineBoot1() {

        jasmineBuilder.createGlobalVariables(context);
        jasmineBuilder.loadJasmine(context);
        jasmineBuilder.loadJasmineBoot0(context);
        jasmineBuilder.loadJasmineBoot1(context);

        validateGlobalVariables();
    }

    @Test
    public void testLoadJasmineBoot0() {

        jasmineBuilder.createGlobalVariables(context);
        jasmineBuilder.loadJasmine(context);
        jasmineBuilder.loadJasmineBoot0(context);

        validateGlobalVariables();
    }

    @Test
    public void testLoadJasmine() {

        jasmineBuilder.createGlobalVariables(context);
        jasmineBuilder.loadJasmine(context);

        final Value globalBindings = context.getBindings("js");

        final Value global = globalBindings.getMember("global");

        assertThat(global).as("Null Global").isNotNull();
        assertThat(global.isNull()).as("Global is Null").isFalse();

        final Value jasmineRequire = globalBindings.getMember("jasmineRequire");

        assertThat(jasmineRequire).as("Null Jasmine Require").isNotNull();
        assertThat(jasmineRequire.isNull()).as("Jasmine Require is Null").isFalse();

        final Value getJasmineRequireObj = globalBindings.getMember("getJasmineRequireObj");

        assertThat(getJasmineRequireObj).as("Null Get Jasmine Require Object").isNotNull();
        assertThat(getJasmineRequireObj.isNull()).as("Get Jasmine Require Object is Null").isFalse();
        assertThat(getJasmineRequireObj.canExecute()).as("Get Jasmine Require Object Can Execute").isTrue();
    }

    @Test
    public void testCreateGlobalVariables() {

        jasmineBuilder.createGlobalVariables(context);

        final Value globalBindings = context.getBindings("js");

        final Value global = globalBindings.getMember("global");

        assertThat(global).as("Null Global").isNotNull();
        assertThat(global.isNull()).as("Global is Null").isFalse();
    }

    @Test
    public void testFindFileWithStartingSlashOnClasspath() {

        final File file = jasmineBuilder.findFile("/" + TEST_FILE);

        assertThat(file).as("Test File: " + file.getAbsolutePath()).isNotNull();
        assertThat(file.exists()).as("Test File exists: " + file.getAbsolutePath()).isTrue();
    }

    @Test
    public void testFindFileWithoutStartingSlashOnClasspath() {

        final File file = jasmineBuilder.findFile(TEST_FILE_1);

        assertThat(file).as("Test File: " + file.getAbsolutePath()).isNotNull();
        assertThat(file.exists()).as("Test File exists: " + file.getAbsolutePath()).isTrue();
    }

    @Test
    public void testFindFileWithStartingSlashOnFileSystem() {

        final File file = jasmineBuilder.findFile("src/test/resources/" + TEST_FILE);

        assertThat(file).as("Test File: " + file.getAbsolutePath()).isNotNull();
        assertThat(file.exists()).as("Test File exists: " + file.getAbsolutePath()).isTrue();

    }

    @Test
    public void testFindFileWithoutStartingSlashOnFileSystem() {

        final File file = jasmineBuilder.findFile(System.getProperty("user.dir") + "/src/test/resources/" + TEST_FILE_1);

        assertThat(file).as("Test File: " + file.getAbsolutePath()).isNotNull();
        assertThat(file.exists()).as("Test File exists: " + file.getAbsolutePath()).isTrue();
    }

    private void validateGlobalVariables() {

        final Value globalBindings = context.getBindings("js");

        final Value global = globalBindings.getMember("global");

        assertThat(global).as("Null Global").isNotNull();
        assertThat(global.isNull()).as("Global is Null").isFalse();

        final Value jasmineRequire = globalBindings.getMember("jasmineRequire");

        assertThat(jasmineRequire).as("Null Jasmine Require").isNotNull();
        assertThat(jasmineRequire.isNull()).as("Jasmine Require is Null").isFalse();

        final Value getJasmineRequireObj = globalBindings.getMember("getJasmineRequireObj");

        assertThat(getJasmineRequireObj).as("Null Get Jasmine Require Object").isNotNull();
        assertThat(getJasmineRequireObj.isNull()).as("Get Jasmine Require Object is Null").isFalse();
        assertThat(getJasmineRequireObj.canExecute()).as("Get Jasmine Require Object Can Execute").isTrue();

        final Value jasmine = globalBindings.getMember("jasmine");

        assertThat(jasmine).as("Null Jasmine").isNotNull();
        assertThat(jasmine.isNull()).as("jasmine is Null").isFalse();
        assertThat(jasmine.canExecute()).as("jasmine Can Execute").isFalse();

        final Value onerror = globalBindings.getMember("onerror");

        assertThat(onerror).as("Null onerror").isNotNull();
        assertThat(onerror.isNull()).as("onerror is Null").isFalse();
        assertThat(onerror.canExecute()).as("onerror Can Execute").isTrue();

        final Value describe = globalBindings.getMember("describe");

        assertThat(describe).as("Null describe").isNotNull();
        assertThat(describe.isNull()).as("describe is Null").isFalse();
        assertThat(describe.canExecute()).as("describe Can Execute").isTrue();

        final Value xdescribe = globalBindings.getMember("xdescribe");

        assertThat(xdescribe).as("Null xdescribe").isNotNull();
        assertThat(xdescribe.isNull()).as("xdescribe is Null").isFalse();
        assertThat(xdescribe.canExecute()).as("xdescribe Can Execute").isTrue();

        final Value fdescribe = globalBindings.getMember("fdescribe");

        assertThat(fdescribe).as("Null fdescribe").isNotNull();
        assertThat(fdescribe.isNull()).as("fdescribe is Null").isFalse();
        assertThat(fdescribe.canExecute()).as("fdescribe Can Execute").isTrue();

        final Value it = globalBindings.getMember("it");

        assertThat(it).as("Null it").isNotNull();
        assertThat(it.isNull()).as("it is Null").isFalse();
        assertThat(it.canExecute()).as("it Can Execute").isTrue();

        final Value xit = globalBindings.getMember("xit");

        assertThat(xit).as("Null xit").isNotNull();
        assertThat(xit.isNull()).as("xit is Null").isFalse();
        assertThat(xit.canExecute()).as("xit Can Execute").isTrue();

        final Value fit = globalBindings.getMember("fit");

        assertThat(fit).as("Null fit").isNotNull();
        assertThat(fit.isNull()).as("fit is Null").isFalse();
        assertThat(fit.canExecute()).as("fit Can Execute").isTrue();

        final Value beforeEach = globalBindings.getMember("beforeEach");

        assertThat(beforeEach).as("Null beforeEach").isNotNull();
        assertThat(beforeEach.isNull()).as("beforeEach is Null").isFalse();
        assertThat(beforeEach.canExecute()).as("beforeEach Can Execute").isTrue();

        final Value afterEach = globalBindings.getMember("afterEach");

        assertThat(afterEach).as("Null afterEach").isNotNull();
        assertThat(afterEach.isNull()).as("afterEach is Null").isFalse();
        assertThat(afterEach.canExecute()).as("afterEach Can Execute").isTrue();

        final Value beforeAll = globalBindings.getMember("beforeAll");

        assertThat(beforeAll).as("Null beforeAll").isNotNull();
        assertThat(beforeAll.isNull()).as("beforeAll is Null").isFalse();
        assertThat(beforeAll.canExecute()).as("beforeAll Can Execute").isTrue();

        final Value afterAll = globalBindings.getMember("afterAll");

        assertThat(afterAll).as("Null afterAll").isNotNull();
        assertThat(afterAll.isNull()).as("afterAll is Null").isFalse();
        assertThat(afterAll.canExecute()).as("afterAll Can Execute").isTrue();

        final Value setSpecProperty = globalBindings.getMember("setSpecProperty");

        assertThat(setSpecProperty).as("Null setSpecProperty").isNotNull();
        assertThat(setSpecProperty.isNull()).as("setSpecProperty is Null").isFalse();
        assertThat(setSpecProperty.canExecute()).as("setSpecProperty Can Execute").isTrue();

        final Value setSuiteProperty = globalBindings.getMember("setSuiteProperty");

        assertThat(setSuiteProperty).as("Null setSuiteProperty").isNotNull();
        assertThat(setSuiteProperty.isNull()).as("setSuiteProperty is Null").isFalse();
        assertThat(setSuiteProperty.canExecute()).as("setSuiteProperty Can Execute").isTrue();

        final Value expect = globalBindings.getMember("expect");

        assertThat(expect).as("Null expect").isNotNull();
        assertThat(expect.isNull()).as("expect is Null").isFalse();
        assertThat(expect.canExecute()).as("expect Can Execute").isTrue();

        final Value expectAsync = globalBindings.getMember("expectAsync");

        assertThat(expectAsync).as("Null expectAsync").isNotNull();
        assertThat(expectAsync.isNull()).as("expectAsync is Null").isFalse();
        assertThat(expectAsync.canExecute()).as("expectAsync Can Execute").isTrue();

        final Value pending = globalBindings.getMember("pending");

        assertThat(pending).as("Null pending").isNotNull();
        assertThat(pending.isNull()).as("pending is Null").isFalse();
        assertThat(pending.canExecute()).as("pending Can Execute").isTrue();

        final Value fail = globalBindings.getMember("fail");

        assertThat(fail).as("Null fail").isNotNull();
        assertThat(fail.isNull()).as("fail is Null").isFalse();
        assertThat(fail.canExecute()).as("fail Can Execute").isTrue();

        final Value spyOn = globalBindings.getMember("spyOn");

        assertThat(spyOn).as("Null spyOn").isNotNull();
        assertThat(spyOn.isNull()).as("spyOn is Null").isFalse();
        assertThat(spyOn.canExecute()).as("spyOn Can Execute").isTrue();

        final Value spyOnProperty = globalBindings.getMember("spyOnProperty");

        assertThat(spyOnProperty).as("Null spyOnProperty").isNotNull();
        assertThat(spyOnProperty.isNull()).as("spyOnProperty is Null").isFalse();
        assertThat(spyOnProperty.canExecute()).as("spyOnProperty Can Execute").isTrue();

        final Value spyOnAllFunctions = globalBindings.getMember("spyOnAllFunctions");

        assertThat(spyOnAllFunctions).as("Null spyOnAllFunctions").isNotNull();
        assertThat(spyOnAllFunctions.isNull()).as("spyOnAllFunctions is Null").isFalse();
        assertThat(spyOnAllFunctions.canExecute()).as("spyOnAllFunctions Can Execute").isTrue();

        final Value jsApiReporter = globalBindings.getMember("jsApiReporter");

        assertThat(jsApiReporter).as("Null jsApiReporter").isNotNull();
        assertThat(jsApiReporter.isNull()).as("jsApiReporter is Null").isFalse();
        assertThat(jsApiReporter.canExecute()).as("jsApiReporter Can Execute").isFalse();
    }

}
