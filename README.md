# HyperCommand [![Maven Central](https://img.shields.io/maven-central/v/io.github.pixelstudios-dev/PixelStudiosCore.svg?color=orange)](https://central.sonatype.com/artifact/io.github.pixelstudios-dev/PixelStudiosCore) [![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/PixelStudios-Dev/PixelStudiosCore)

Una libreria de comandos para Hytale.

---

## Add the library to your project

### Gradle

Si you are using gradle with kotlin, you will need to add the following to your `build.gradle`:

```gradle
implementation("io.github.Danixd12:HyperCommand:VERSION")
```

### Maven

If instead, you prefer maven, add this to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.Danixd12</groupId>
    <artifactId>HyperCommand</artifactId>
    <version>VERSION</version>
</dependency>
```

### Other build systems

Don't worry, on the [Maven Central](https://central.sonatype.com/artifact/io.github.pixelstudios-dev/PixelStudiosCore) you can find all the snippets for other build systems

*Latest version is available at the [releases](https://github.com/PixelStudios-Dev/PixelStudiosCore/releases) tab*

## Using the library

The main goal is to abstract the creation of commands.

This library allows you to make complex commands in a few lines. Let's see an example:

```java
@Command(
        name = "test",
        aliases = {"t", "t1"},
        permissions = "cmd.test"
)
public class TestCommand {

    CommandContext ctx;

    @ParentCommand
    void parent() {

        ctx.sendMessage(Message.raw(
                "Hello " + ctx.sender() + ", from parent!"
        ));

    }

    @SubCommand(
            name = "subtest",
            permissions = "cmd.subtest"
    )
    void hello(@Arg                  String name,
               @Arg(required = true) int age) {

        ctx.sendMessage(Message.raw(
                "Hello " + name + ", you are " + age + " years old"
        ));

    }

}
```

The documentation will improve over time, and will eventually get his own wiki page.

Thank you for reading.

## Licencia

This library is brought to you under an Open Source Library, for more info about what you can and can't do, please read [LICENSE](https://github.com/PixelStudios-Dev/PixelStudiosCore/blob/main/LICENSE)