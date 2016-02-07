# Hack the Local Treasure (server)

## How to set up

**Configuration**

On running a batch to create DB, replace API KEY for Docomo in RegisterDBTask.java

**Dependencies**

Java8

Other dependencies are solved by gradle.

**Database configuration**

None. H2 Database is used.

## How to run

Type the following command on the project root directory. Executable jar file is created under build/libs/.

> \# gradle build

Move to the directory where the jar file is created.

> \# cd build/libs

Put db file on the directory.

Type the following command.

> \# jar -jar [jar file name]

Web api is served at http://localhost:9001

## How to run tests

TODO
