# Hack the Local Treasure (server)

## How to set up

**Configuration**

On running a batch to create DB, replace API KEY for Docomo in RegisterDBTask.java

None

**Dependencies**

Java8

Other dependencies are solved by gradle.

**Database configuration**

None. H2 Database is used.

## How to run

1. Type the following command on your project root directory. Executable jar file is created under build/libs/.
> \# gradle build
2. Move to the directory which jar file is created.
> \# cd build/libs
3. Put db file.
4. Type the following command.
> \# jar -jar [jar file name]
5. Web api is served at http://localhost:9001

## How to run tests

TODO
