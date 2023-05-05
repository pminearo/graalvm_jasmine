package org.graalvm.jasmine;

import static org.assertj.core.api.Assertions.*;

import java.io.File;

import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class JasmineRunnerTest {

    @Test
    public void testRun() throws Exception {

        final JasmineRunner scriptRunner = new JasmineRunner();

        scriptRunner.initializeJasmine();
        scriptRunner.run();

        final Value globalBindings = scriptRunner.getGlobalBindings();
        final Value report = globalBindings.getMember("jsApiReporter");

        final Value started = report.getMember("started");
        final Value finished = report.getMember("finished");
        final Value runDetails = report.getMember("runDetails");
        final Value status = report.getMember("status").execute();
        final Value jasmineStarted = report.getMember("jasmineStarted").execute();
        final Value jasmineDone = report.getMember("jasmineDone").execute();
        final Value suiteStarted = report.getMember("suiteStarted");
        final Value suiteDone = report.getMember("suiteDone");
        final Value suiteResults = report.getMember("suiteResults");
        final Value suites = report.getMember("suites").execute();
        final Value specDone = report.getMember("specDone").execute();
        final Value specResults = report.getMember("specResults").execute();
        final Value specs = report.getMember("specs").execute();
        final Value executionTime = report.getMember("executionTime").execute();

        System.out.println("########################");
        System.out.println("#    Jasmine Report    #");
        System.out.println("########################");
        System.out.println();
        System.out.println("Started: " + started);
        System.out.println("Finished :" + finished);
        System.out.println("Run Details: " + runDetails);
        System.out.println("Status: " + status);
        System.out.println("Jasmine Started:" + jasmineStarted);
        System.out.println("Jasmine Done:" + jasmineDone);
//        System.out.println("Suite Started:" + suiteStarted);
//        System.out.println("Suite Done:" + suiteDone);
//        System.out.println("Suite Results:" + suiteResults);
        System.out.println("Suites: " + suites);
        System.out.println("Specs Done:" + specDone);
        System.out.println("Spec Results:" + specResults);
        System.out.println("Specs: " + specs);
        System.out.println("Execution Time:" + executionTime);
        System.out.println();
        System.out.println("########################");
        System.out.println("########################");
    }

    /*
     * The Tests below were used to ensure each method worked before executing a `testRun()`.
     *
     * These Tests are disabled because we only want `testRun()` to execute when
     * building the project. This test calls all of the methods in the tests below.
     *
     * Remove `@Disabled` if you want to execute any of the tests specifically.
     *
     */

    @Test
    @Disabled
    public void testInitializeJasmine() throws Exception {

        final JasmineRunner scriptRunner = new JasmineRunner();
        scriptRunner.initializeJasmine();

        validateGlobalVariables(scriptRunner);
    }

    @Test
    @Disabled
    public void testLoadTest() throws Exception {

        final JasmineRunner scriptRunner = new JasmineRunner();
        scriptRunner.createGlobalVariables();
        scriptRunner.loadJasmine();
        scriptRunner.loadJasmineBoot0();
        scriptRunner.loadJasmineBoot1();
        scriptRunner.loadTest();

        validateGlobalVariables(scriptRunner);
    }

    @Test
    @Disabled
    public void testLoadJasmineBoot1() throws Exception {

        final JasmineRunner scriptRunner = new JasmineRunner();
        scriptRunner.createGlobalVariables();
        scriptRunner.loadJasmine();
        scriptRunner.loadJasmineBoot0();
        scriptRunner.loadJasmineBoot1();

        validateGlobalVariables(scriptRunner);
    }

    @Test
    @Disabled
    public void testLoadJasmineBoot0() throws Exception {

        final JasmineRunner scriptRunner = new JasmineRunner();
        scriptRunner.createGlobalVariables();
        scriptRunner.loadJasmine();
        scriptRunner.loadJasmineBoot0();

        validateGlobalVariables(scriptRunner);
    }

    @Test
    @Disabled
    public void testLoadJasmine() throws Exception {

        final JasmineRunner scriptRunner = new JasmineRunner();
        scriptRunner.createGlobalVariables();
        scriptRunner.loadJasmine();

        final Value global = scriptRunner.getGlobalBindings().getMember("global");

        assertThat(global).as("Null Global").isNotNull();
        assertThat(global.isNull()).as("Global is Null").isFalse();

        final Value jasmineRequire = scriptRunner.getGlobalBindings().getMember("jasmineRequire");

        assertThat(jasmineRequire).as("Null Jasmine Require").isNotNull();
        assertThat(jasmineRequire.isNull()).as("Jasmine Require is Null").isFalse();

        final Value getJasmineRequireObj = scriptRunner.getGlobalBindings().getMember("getJasmineRequireObj");

        assertThat(getJasmineRequireObj).as("Null Get Jasmine Require Object").isNotNull();
        assertThat(getJasmineRequireObj.isNull()).as("Get Jasmine Require Object is Null").isFalse();
        assertThat(getJasmineRequireObj.canExecute()).as("Get Jasmine Require Object Can Execute").isTrue();
    }

    @Test
    @Disabled
    public void testCreateGlobalVariables() {

        final JasmineRunner scriptRunner = new JasmineRunner();
        scriptRunner.createGlobalVariables();

        final Value global = scriptRunner.getGlobalBindings().getMember("global");

        assertThat(global).as("Null Global").isNotNull();
        assertThat(global.isNull()).as("Global is Null").isFalse();
    }

    @Test
    @Disabled
    public void testFindFile() {

        final JasmineRunner scriptRunner = new JasmineRunner();

        File file = scriptRunner.findFile(JasmineRunner.JASMINE);

        assertThat(file).as("Jasmine File").isNotNull();
        assertThat(file.exists()).as("Jasmine File Exists").isTrue();

        file = scriptRunner.findFile(JasmineRunner.JASMINE_BOOT_0);

        assertThat(file).as("Jasmine Boot 0 File").isNotNull();
        assertThat(file.exists()).as("Jasmine Boot 0 File Exists").isTrue();

        file = scriptRunner.findFile(JasmineRunner.JASMINE_BOOT_1);

        assertThat(file).as("Jasmine Boot 1 File").isNotNull();
        assertThat(file.exists()).as("Jasmine Boot 1 File Exists").isTrue();

        file = scriptRunner.findFile(JasmineRunner.TEST_FILE);

        assertThat(file).as("Test File").isNotNull();
        assertThat(file.exists()).as("Test File Exists").isTrue();
    }

    private static void validateGlobalVariables(final JasmineRunner scriptRunner) {

        final Value global = scriptRunner.getGlobalBindings().getMember("global");

        assertThat(global).as("Null Global").isNotNull();
        assertThat(global.isNull()).as("Global is Null").isFalse();

        final Value jasmineRequire = scriptRunner.getGlobalBindings().getMember("jasmineRequire");

        assertThat(jasmineRequire).as("Null Jasmine Require").isNotNull();
        assertThat(jasmineRequire.isNull()).as("Jasmine Require is Null").isFalse();

        final Value getJasmineRequireObj = scriptRunner.getGlobalBindings().getMember("getJasmineRequireObj");

        assertThat(getJasmineRequireObj).as("Null Get Jasmine Require Object").isNotNull();
        assertThat(getJasmineRequireObj.isNull()).as("Get Jasmine Require Object is Null").isFalse();
        assertThat(getJasmineRequireObj.canExecute()).as("Get Jasmine Require Object Can Execute").isTrue();

        final Value jasmine = scriptRunner.getGlobalBindings().getMember("jasmine");

        assertThat(jasmine).as("Null Jasmine").isNotNull();
        assertThat(jasmine.isNull()).as("jasmine is Null").isFalse();
        assertThat(jasmine.canExecute()).as("jasmine Can Execute").isFalse();

        final Value onerror = scriptRunner.getGlobalBindings().getMember("onerror");

        assertThat(onerror).as("Null onerror").isNotNull();
        assertThat(onerror.isNull()).as("onerror is Null").isFalse();
        assertThat(onerror.canExecute()).as("onerror Can Execute").isTrue();

        final Value describe = scriptRunner.getGlobalBindings().getMember("describe");

        assertThat(describe).as("Null describe").isNotNull();
        assertThat(describe.isNull()).as("describe is Null").isFalse();
        assertThat(describe.canExecute()).as("describe Can Execute").isTrue();

        final Value xdescribe = scriptRunner.getGlobalBindings().getMember("xdescribe");

        assertThat(xdescribe).as("Null xdescribe").isNotNull();
        assertThat(xdescribe.isNull()).as("xdescribe is Null").isFalse();
        assertThat(xdescribe.canExecute()).as("xdescribe Can Execute").isTrue();

        final Value fdescribe = scriptRunner.getGlobalBindings().getMember("fdescribe");

        assertThat(fdescribe).as("Null fdescribe").isNotNull();
        assertThat(fdescribe.isNull()).as("fdescribe is Null").isFalse();
        assertThat(fdescribe.canExecute()).as("fdescribe Can Execute").isTrue();

        final Value it = scriptRunner.getGlobalBindings().getMember("it");

        assertThat(it).as("Null it").isNotNull();
        assertThat(it.isNull()).as("it is Null").isFalse();
        assertThat(it.canExecute()).as("it Can Execute").isTrue();

        final Value xit = scriptRunner.getGlobalBindings().getMember("xit");

        assertThat(xit).as("Null xit").isNotNull();
        assertThat(xit.isNull()).as("xit is Null").isFalse();
        assertThat(xit.canExecute()).as("xit Can Execute").isTrue();

        final Value fit = scriptRunner.getGlobalBindings().getMember("fit");

        assertThat(fit).as("Null fit").isNotNull();
        assertThat(fit.isNull()).as("fit is Null").isFalse();
        assertThat(fit.canExecute()).as("fit Can Execute").isTrue();

        final Value beforeEach = scriptRunner.getGlobalBindings().getMember("beforeEach");

        assertThat(beforeEach).as("Null beforeEach").isNotNull();
        assertThat(beforeEach.isNull()).as("beforeEach is Null").isFalse();
        assertThat(beforeEach.canExecute()).as("beforeEach Can Execute").isTrue();

        final Value afterEach = scriptRunner.getGlobalBindings().getMember("afterEach");

        assertThat(afterEach).as("Null afterEach").isNotNull();
        assertThat(afterEach.isNull()).as("afterEach is Null").isFalse();
        assertThat(afterEach.canExecute()).as("afterEach Can Execute").isTrue();

        final Value beforeAll = scriptRunner.getGlobalBindings().getMember("beforeAll");

        assertThat(beforeAll).as("Null beforeAll").isNotNull();
        assertThat(beforeAll.isNull()).as("beforeAll is Null").isFalse();
        assertThat(beforeAll.canExecute()).as("beforeAll Can Execute").isTrue();

        final Value afterAll = scriptRunner.getGlobalBindings().getMember("afterAll");

        assertThat(afterAll).as("Null afterAll").isNotNull();
        assertThat(afterAll.isNull()).as("afterAll is Null").isFalse();
        assertThat(afterAll.canExecute()).as("afterAll Can Execute").isTrue();

        final Value setSpecProperty = scriptRunner.getGlobalBindings().getMember("setSpecProperty");

        assertThat(setSpecProperty).as("Null setSpecProperty").isNotNull();
        assertThat(setSpecProperty.isNull()).as("setSpecProperty is Null").isFalse();
        assertThat(setSpecProperty.canExecute()).as("setSpecProperty Can Execute").isTrue();

        final Value setSuiteProperty = scriptRunner.getGlobalBindings().getMember("setSuiteProperty");

        assertThat(setSuiteProperty).as("Null setSuiteProperty").isNotNull();
        assertThat(setSuiteProperty.isNull()).as("setSuiteProperty is Null").isFalse();
        assertThat(setSuiteProperty.canExecute()).as("setSuiteProperty Can Execute").isTrue();

        final Value expect = scriptRunner.getGlobalBindings().getMember("expect");

        assertThat(expect).as("Null expect").isNotNull();
        assertThat(expect.isNull()).as("expect is Null").isFalse();
        assertThat(expect.canExecute()).as("expect Can Execute").isTrue();

        final Value expectAsync = scriptRunner.getGlobalBindings().getMember("expectAsync");

        assertThat(expectAsync).as("Null expectAsync").isNotNull();
        assertThat(expectAsync.isNull()).as("expectAsync is Null").isFalse();
        assertThat(expectAsync.canExecute()).as("expectAsync Can Execute").isTrue();

        final Value pending = scriptRunner.getGlobalBindings().getMember("pending");

        assertThat(pending).as("Null pending").isNotNull();
        assertThat(pending.isNull()).as("pending is Null").isFalse();
        assertThat(pending.canExecute()).as("pending Can Execute").isTrue();

        final Value fail = scriptRunner.getGlobalBindings().getMember("fail");

        assertThat(fail).as("Null fail").isNotNull();
        assertThat(fail.isNull()).as("fail is Null").isFalse();
        assertThat(fail.canExecute()).as("fail Can Execute").isTrue();

        final Value spyOn = scriptRunner.getGlobalBindings().getMember("spyOn");

        assertThat(spyOn).as("Null spyOn").isNotNull();
        assertThat(spyOn.isNull()).as("spyOn is Null").isFalse();
        assertThat(spyOn.canExecute()).as("spyOn Can Execute").isTrue();

        final Value spyOnProperty = scriptRunner.getGlobalBindings().getMember("spyOnProperty");

        assertThat(spyOnProperty).as("Null spyOnProperty").isNotNull();
        assertThat(spyOnProperty.isNull()).as("spyOnProperty is Null").isFalse();
        assertThat(spyOnProperty.canExecute()).as("spyOnProperty Can Execute").isTrue();

        final Value spyOnAllFunctions = scriptRunner.getGlobalBindings().getMember("spyOnAllFunctions");

        assertThat(spyOnAllFunctions).as("Null spyOnAllFunctions").isNotNull();
        assertThat(spyOnAllFunctions.isNull()).as("spyOnAllFunctions is Null").isFalse();
        assertThat(spyOnAllFunctions.canExecute()).as("spyOnAllFunctions Can Execute").isTrue();

        final Value jsApiReporter = scriptRunner.getGlobalBindings().getMember("jsApiReporter");

        assertThat(jsApiReporter).as("Null jsApiReporter").isNotNull();
        assertThat(jsApiReporter.isNull()).as("jsApiReporter is Null").isFalse();
        assertThat(jsApiReporter.canExecute()).as("jsApiReporter Can Execute").isFalse();
    }

}
