---

app.version: 1004

---


# cadc-target-resolver
Name resolving proxy that calls multiple target name resolvers.

<a href="https://travis-ci.org/opencadc/cadc-target-resolver"><img src="https://travis-ci.org/opencadc/cadc-target-resolver.svg?branch=master" /></a>


# Running

## Docker

The currently containerized version is on the Docker Hub at [https://hub.docker.com/r/opencadc/cadc-target-resolver/](https://hub.docker.com/r/opencadc/cadc-target-resolver/).

```
docker run -p 8080:8080 -p 5555:5555 opencadc/cadc-target-resolver
```

will start the server.  You can point your browser to http://localhost:8080/cadc-target-resolver/ for the documentation page.

## Existing deployment of Tomcat

You can build the WAR yourself with

```
gradle clean build
```

Then deploy the `build/libs/cadc-target-resolver##1004.war` to your existing Servlet Container.


## Integration tests

In the `integrationTest` folder, you can run

```
export REGISTRY_HOST=<your registry host>
docker-compose up -d
```

To start a proxy server and the resolver web application.

Then in the working directory, run:

```
gradle -i -Dca.nrc.cadc.reg.client.RegistryClient=<your registry host>
```

Where `<your registry host>` matches the one running the server above.
