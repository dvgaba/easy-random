# easy-random-protobuf
Extension for [Easy Random library](https://github.com/j-easy/easy-random) adding support for [Protocol buffers](https://developers.google.com/protocol-buffers).

## Usage

This library declares a [SPI provider](https://github.com/j-easy/easy-random/wiki/Grouping-Randomizers), so Easy Random will detect it automatically.

If you want to explicitly use it:

```java
EasyRandomParameters parameters = new EasyRandomParameters()
        .randomizerRegistry(new ProtobufRandomizerRegistry());

EasyRandom easyRandom = new EasyRandom(parameters);

Person randomPerson = easyRandom.nextObject(Person.class); // With Person being a generated class from a .proto file
```
