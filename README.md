# Code Climate Sonar Wrapper

[![CircleCI](https://circleci.com/gh/codeclimate/sonar-wrapper.svg?style=svg&circle-token=c84150ff05586b0a4671cc70858f24e43d50b10f)](https://circleci.com/gh/codeclimate/sonar-wrapper)

`sonar-wrapper` is the base library for Sonar based engines.
It wraps [Sonarlint](http://www.sonarlint.org) in standalone mode.

## Tests
```
make test
```

## Usage

You can use the [codeclimate-sonar-php](https://github.com/codeclimate/codeclimate-sonar-php) repo as an example for building a new sonar based engine.
The important aspects are listed below:

1. Use this wrapper lib. Make sure your `build.gradle` has the following entries:
```groovy
repositories {
  jcenter()
  maven { url 'https://jitpack.io' }
}

dependencies {
  compile("com.github.codeclimate:sonar-wrapper:master-SNAPSHOT")
}
```
2.  Add the plugin lib and make sure it is copied to the `build/plugins` directory as part of the `build` task:
```groovy
task copyPlugins(type: Copy) {
  into "${buildDir}/plugins"
  from configurations.testCompile
  include "sonar-*-plugin*.jar"
}

build.dependsOn(copyPlugins)

dependencies {
  // ...
  compile("org.sonarsource.php:sonar-php-plugin:2.10.0.2087")
}
```
3. Running: The wrapper overrides a few classes from the sonar libraries which makes classpath order something very important to pay attention:
`bin/codeclimate-sonar`:
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
`bin/codeclimate-sonar /code /config.json`

## Sonar Documentation

http://www.sonarlint.org/commandline
http://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner

Issue Tracker: http://jira.sonarsource.com/browse/SLCLI

## Copyright

See [LICENSE](LICENSE)
