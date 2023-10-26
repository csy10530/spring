FROM openjdk:8-jdk-alpine
VOLUME /tmp
# ADD /target/SpringBootRESTDemo-0.0.1-SNAPSHOT.jar SpringBootRESTDemo-0.0.1-SNAPSHOT.jar
ADD . /
# Install Maven
RUN apk add --no-cache curl tar bash
ARG MAVEN_VERSION=3.3.9
ARG USER_HOME_DIR="/root"
RUN mkdir -p /usr/share/maven && \
curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
ADD /lib/ojdbc6.jar ojdbc6.jar
RUN mvn install:install-file -Dfile=ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar
RUN mvn clean package -Dmaven.test.skip
EXPOSE 8080
ENTRYPOINT ["java","-jar","./target/SpringBootRESTDemo-0.0.1-SNAPSHOT.jar"]
