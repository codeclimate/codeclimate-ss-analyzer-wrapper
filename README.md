# Code Climate Sonar Wrapper

`sonar-wrapper` is the base library for Sonar based engines.
It wraps [Sonarlint](http://www.sonarlint.org) in standalone mode.

## Tests
```
make test
```

## Usage

You can use the [codeclimate-sonar-php](https://github.com/codeclimate/codeclimate-sonar-php) repo as an example for building a new sonar based engine.
The important aspects are listed below.

### Library

Make sure your `build.gradle` has the following entries:
```groovy
repositories {
  jcenter()
  maven { url 'https://jitpack.io' }
}

dependencies {
  compile("com.github.codeclimate:sonar-wrapper:master-SNAPSHOT")
}
```

### Plugin

Add the plugin lib and make sure it is copied to `build/plugins`:
```groovy
task copyPlugins(type: Copy) {
  into "${buildDir}/plugins"
  from configurations.testCompile
  include "sonar-*-plugin*.jar"
}

build.dependsOn(copyPlugins)

dependencies {
  // ...
  testCompile("org.sonarsource.java:sonar-java-plugin:4.14.0.11784")
}
```

### Running

The wrapper overrides a few classes from the sonar libraries which makes classpath order something very important to pay attention:
```sh
#!/usr/bin/env sh

BUILD_DIR=$(dirname $0)
APP=$(find ${BUILD_DIR}/libs -name "codeclimate-sonar-php.jar" | head -n1)
WRAPPER=$(find ${BUILD_DIR}/libs -name "sonar-wrapper*.jar" | head -n1)
CORE=$(find ${BUILD_DIR}/libs -name "sonarlint-core*.jar" -or -name "sonarlint-client-api*.jar" | tr "\n" ":")
LIBS=$(find ${BUILD_DIR}/libs -name "*.jar" | tr "\n" ":")

CODE_DIR=$1; shift
CONFIG_FILE=$1; shift

java \
  -noverify \
  -cp ${APP}:${WRAPPER}:${CORE}:${LIBS} \
  -Djava.awt.headless=true  \
  -Dsonarlint.home="${BUILD_DIR}"  \
  -Dproject.home="${CODE_DIR}"  \
  -Dconfig="${CONFIG_FILE}"  \
  -Dorg.freemarker.loggerLibrary=none  \
  cc.App $@
```

## Sonar Documentation

http://www.sonarlint.org/commandline
http://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner

Issue Tracker: http://jira.sonarsource.com/browse/SLCLI

## Copyright

See [LICENSE](LICENSE)
