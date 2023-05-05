# Jasmine Implementation in GraalVM

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

# Jasmine Notes

I pulled the Jasmine (4.6.0) files from: https://cdnjs.com/libraries/jasmine
* jasmine.js
* boot0.js
* boot1.js

These files are located in `src/main/resources`.
The test is located in `src/test/resources`.

## jasmine.js
You will notice I have commented out some lines in this file.  
I put a "TODO: PVM:" by the comment to signify that I commented this part of the file.
There were already 9 other "TODO" entries in the file.
You will see these on lines 4099-4121.
This was done to remove any reference to Browser, or Node, functionality as we are in a standalone application.

## boot0.js
You will notice more "TODO: PVM:".  I edited this file to verify that the tests are in a Browser, or Node.js, environment.
If the tests are not, then it skips using Browser, or Node.js, specific functionality.

## boot1.js
You will notice that most of this file is commented out.  This is because this file assumes that the tests are
Browser, or Node.js, based.  

## JasmineRunner
This Java class is used to build the GraalVM environment with Jasmine and then executes the tests.
