describe("Integration Test Describe 1", function () {

    console.log("Starting Integration Test Describe 1");

    beforeAll(function () {
        console.log("***** Before All 1");
    });

    beforeEach(function () {
        console.log("##### Before Each 1");
    });

    it("Integration Test Spec 1", function () {
        console.log("Starting Integration Test Spec 1");

        expect(true).toBe(false);

        console.log("Finished Integration Test Spec 1");
    });


    afterEach(function () {
        console.log("##### After Each 1");
    });

    afterAll(function () {
        console.log("***** After All 1");
    });

    console.log("Finished Integration Test Describe 1");

});
