[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Apache 2.0 License][license-shield]][license-url]



<h1 align="center">Fetch4j</h1>
<p align="center">
  A light-weight library that bring a Fetch-like API to Java.
  <br />
  <a href="https://insprill.net/javadocs/fetch4j"><strong>View Javadocs »</strong></a>
  <br />
  <br />
  <a href="https://github.com/Insprill/fetch4j/issues">Report Bugs</a>
  ·
  <a href="https://github.com/Insprill/fetch4j/issues">Request Features</a>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
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

[![Maven Central][maven-central-shield]][license-url]
#### Maven

```xml
<dependency>
    <groupId>net.insprill</groupId>
    <artifactId>fetch4j</artifactId>
    <version>version</version>
</dependency>
```

#### Gradle

```groovy
dependencies {
    implementation 'net.insprill:fetch4j:version'
}
```

All versions can be found [here](https://jitpack.io/#Insprill/fetch4j).

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
        .method("POST")
        .body("Howdy"));
```

#### Simple POST with JSON

```java
import static net.insprill.fetch4j.Fetch.fetch;
import static net.insprill.fetch4j.Params.params;

Response response = fetch("https://example.com/api/register", params()
        .method("POST")
        .contentType("application/json")
        .body("{\n" +
                  "\"email\": \"me@example.net\",\n" +
                  "\"password\": \"password123\"\n" +
        "}"));
```

## Compiling

To compile fetch4j, you need JDK 8 or higher and an internet connection.  
Clone this repo, then run `./gradlew build` from your terminal.  
You can find the compiled jar in the `build/libs` directory.  
If you wish to install it to your local Maven repository, run `./gradlew publishToMavenLocal` after compiling.



<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Make your changes.
4. Stage your changes (`git add .`)
5. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
6. Push to the Branch (`git push origin feature/AmazingFeature`)
7. Open a Pull Request

<!-- LICENSE -->

## License

Distributed under the Apache 2.0 License. See [`LICENSE`][license-url] for more information.




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
