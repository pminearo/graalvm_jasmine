# Jasmine Implementation in GraalVM

Unfortunatley, Jasmine expects the environment it executes in to be either a browser, or node.js.
Using GraalVM and graal-js, this is not the case.  So, Jasmine needed to be refactored to be a
standalone JavaScript testing framework with no ties to browsers, or node.js.

This project is not intended to be a library that is included as a dependency into a Java project.
It is intended to show how to execute Jasmine in a GraalVM Polyglot Context.  The JavaScript files
have been modified to do so.

If the files in this project are to be used, create a clone of the project and copy the following files into the Java project.
* src/main/resources/jasmine.js
* src/main/resources/boot0.js
* src/main/resources/boot1.js
* src/main/java/org/graalvm/jasmine/JasmineBuilder.java


# Environment

This project was built on:
* MacOS (aarch64)
* GraalVM 22.3.1 - JDK17
* Maven 3.9.0

To Download the project:
```shell
$ git clone https://github.com/pminearo/graalvm_jasmine.git
```

To execute the Test in Jasmine executing in GraalVM with graal-js
```shell
$ cd graalvm_jasmine
$ mvn clean install
```

# Project Notes

I pulled the Jasmine (4.6.0) files from: https://cdnjs.com/libraries/jasmine
* jasmine.js
* boot0.js
* boot1.js

These files are located in `src/main/resources`.
The tests are located in `src/test/resources`.

## jasmine.js
This is the main Jasmine environment. The references to any browser related code including the DOM and node.js were
commented out.  The code was left in place for future reference of what was changed and what was there initially.  
Hopefully, this will make it easier to upgrade Jasmine when later versions come out.

To see the areas that have changed search for "TODO: GRAALVM:".  The "TODO: JASMINE:" comments are from the Jasmine project.

## boot0.js
You will notice more "TODO: GRAALVM:".  This file was edited to verify that the tests are in a Browser, or Node.js, environment.
If the tests are not, then it skips using Browser, or Node.js, specific functionality.

## boot1.js
You will notice that most of this file is commented out.  This is because this file assumes that the tests are
Browser, or Node.js, based.  However, if the need to customize the Jasmine environment in JS, do it here.

## JasmineBuider
This is the Java class that sets up the Jasmine environment in a Polyglot Context and will execute tests. 

## JasmineRunner
This Java class is an example of how to execute a test using GraalVM and Jasmine.

## Test classes
This project includes Integration Tests to ensure Jasmine is working properly in GraalVM.
