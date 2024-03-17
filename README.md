# Zip Gatherer

A proof-of-concept for implementing a Java 22 gatherer
([JEP 461](https://openjdk.org/jeps/461)) for zipping two streams.

## Build

This project requires Java 22 or newer for building.
It comes with the Apache [Maven wrapper](https://github.com/takari/maven-wrapper),
i.e. a Maven distribution will be downloaded automatically, if needed.

Run the following command to build this project:

```shell
./mvnw clean verify
```

On Windows, run the following command:

```shell
mvnw.cmd clean verify
```

Pass the `-Dquick` option to skip all non-essential plug-ins and create the output artifact as quickly as possible:

```shell
./mvnw clean verify -Dquick
```

Run the following command to format the source code and organize the imports as per the project's conventions:

```shell
./mvnw process-sources
```

## License

This code base is available under the Apache License, version 2.
