[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Apache 2.0 License][license-shield]][license-url]




<div align="center">
  <h1>Fetch4J</h1>
  <p>
    A light-weight library that bring a Fetch-like API to Java.
    <br />
    <a href="https://javadoc.io/doc/net.insprill/fetch4j"><strong>View Javadocs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/Insprill/fetch4j/issues">Report Bug</a>
    ·
    <a href="https://github.com/Insprill/fetch4j/issues">Request Feature</a>
    <br />
    <br />
    <b>Notice:</b> 
    <br />
    Fetch4J is now feature-complete. New features will only be added upon request. Bug fixes and maintenance will still be provided.
  </p>
</div>




<!-- TABLE OF CONTENTS -->
<details>
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#compiling">Compiling</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>




<!-- USAGE EXAMPLES -->

## Usage

### Implementing in your project

[![Maven Central][maven-central-shield]][maven-central-url]
#### Maven

```xml
<dependency>
    <groupId>net.insprill</groupId>
    <artifactId>fetch4j</artifactId>
    <version>version</version>
</dependency>
```

#### Gradle (Groovy)

```groovy
dependencies {
    implementation 'net.insprill:fetch4j:version'
}
```

#### Gradle (Kotlin)

```groovy
dependencies {
    implementation("net.insprill:fetch4j:version")
}
```

### Common Usage

#### Simple GET

```java
import static net.insprill.fetch4j.Fetch.fetch;

String body = fetch("https://example.com/").getBody();
```

#### Simple POST

```java
import static net.insprill.fetch4j.Fetch.fetch;
import static net.insprill.fetch4j.Params.params;

Response response = fetch("https://example.com/api/paste", params()
        .method(Method.POST)
        .contentType(ContentType.TEXT)
        .body("Howdy"));
```

### Advanced Usage

Fetch4J supports many parameters to customize requests.
For all that are available, check out the [Javadocs](https://javadoc.io/doc/net.insprill/fetch4j/latest/net/insprill/fetch4j/Params.html>).




<!-- COMPILING -->

## Compiling

To compile Fetch4J, you will need JDK 8 or higher and an internet connection.  
Clone this repo, then run `./gradlew build` from your terminal.  
You can find the compiled jar in the `build/libs` directory.  
If you wish to install it to your local Maven repository, run `./gradlew publishToMavenLocal`.




<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create.  
Any contributions you make are **greatly appreciated**!  
If you're new to contributing to open-source projects, you can follow [this](https://docs.github.com/en/get-started/quickstart/contributing-to-projects) guide.




<!-- LICENSE -->

## License

Distributed under the Apache 2.0 License. See [LICENSE][license-url] for more information.




<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/Insprill/fetch4j.svg?style=for-the-badge
[contributors-url]: https://github.com/Insprill/fetch4j/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Insprill/fetch4j.svg?style=for-the-badge
[forks-url]: https://github.com/Insprill/fetch4j/network/members
[stars-shield]: https://img.shields.io/github/stars/Insprill/fetch4j.svg?style=for-the-badge
[stars-url]: https://github.com/Insprill/fetch4j/stargazers
[issues-shield]: https://img.shields.io/github/issues/Insprill/fetch4j.svg?style=for-the-badge
[issues-url]: https://github.com/Insprill/fetch4j/issues
[license-shield]: https://img.shields.io/github/license/Insprill/fetch4j.svg?style=for-the-badge
[license-url]: https://github.com/Insprill/fetch4j/blob/master/LICENSE
[maven-central-shield]: https://img.shields.io/maven-central/v/net.insprill/fetch4j
[maven-central-url]: https://mvnrepository.com/artifact/net.insprill/fetch4j
