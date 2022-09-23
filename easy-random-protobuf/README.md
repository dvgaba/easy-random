# easy-random-protobuf

[![Apache2 license](http://img.shields.io/badge/license-Apache2-brightgreen.svg?style=flat)](https://opensource.org/licenses/Apache-2.0)
[![Build Status](https://github.com/murdos/easy-random-protobuf/workflows/Java%20CI/badge.svg)](https://github.com/murdos/easy-random-protobuf/actions)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.murdos/easy-random-protobuf.svg)](https://repo1.maven.org/maven2/io/github/murdos/easy-random-protobuf/0.3.0/)
[![Javadocs](https://javadoc.io/badge2/io.github.murdos/easy-random-protobuf/javadoc.svg)](https://javadoc.io/doc/io.github.murdos/easy-random-protobuf)

Extension for [Easy Random library](https://github.com/j-easy/easy-random) adding support for [Protocol buffers](https://developers.google.com/protocol-buffers).

## Maven
```xml
<dependency>
    <groupId>io.github.murdos</groupId>
    <artifactId>easy-random-protobuf</artifactId>
    <version>0.4.0</version>
</dependency>
```

## Usage

This library declares a [SPI provider](https://github.com/j-easy/easy-random/wiki/Grouping-Randomizers), so Easy Random will detect it automatically.

If you want to explicitly use it:

```java
EasyRandomParameters parameters = new EasyRandomParameters()
        .randomizerRegistry(new ProtobufRandomizerRegistry());

EasyRandom easyRandom = new EasyRandom(parameters);

Person randomPerson = easyRandom.nextObject(Person.class); // With Person being a generated class from a .proto file
```

### Building

```
$ git clone https://github.com/murdos/easy-random-protobuf.git
$ cd easy-random-protobuf
$ mvn clean install
```


