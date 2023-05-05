describe("Integration Test Describe", function () {

    console.log("Starting Integration Test Describe");

    beforeAll(function () {
        console.log("***** Before All");
    });

    beforeEach(function () {
        console.log("##### Before All");
    });

    it("Integration Test Spec", function () {
        console.log("Starting Integration Test Spec");

        expect(true).toBe(true);

        console.log("Finished Integration Test Spec");
    });


    afterEach(function () {
        console.log("##### After Each");
    });

    afterAll(function () {
        console.log("***** After All");
    });

    console.log("Finished Integration Test Describe");

}, "IntegrationTest.js");
