# Trino HTTP Requester

A Simple [trino](https://trino.io/) plugin to expose http functions.

## How to use

You may change the value of the `trinoVersion` variable in [build.sbt](build.sbt) file, it should match the trino version where the plugin will be deployed.

To build the jar package, just use the `sbt assembly` command:

```SHELL
$> sbt assembly
```

If succeeded, it will create the folder `target/http-requester`, copy it to your trino plugins directory:

```SHELL
$> cp -r target/http-requester /path/to/your/trino-folder/plugins
```

Then you may just start trino and the plugin will be available.


## Exposed functions

Actually, the only exposed function is `http_get`.

### http_get

The signature is:

```SQL
http_get(httpAddress: VARCHAR, parameters: MAP(VARCHAR, VARCHAR), headers: MAP(VARCHAR, VARCHAR)) -> JSON
```

The parameters are:

* `httpAddress`: The URL to the service endpoint
* `parameters`: A [Map](https://trino.io/docs/current/functions/map.html) of the query parameters names and values
* `headers`: A [Map(https://trino.io/docs/current/functions/map.html)] of the HTTP headers used in request

The function returns a trino [JSON](https://trino.io/docs/current/functions/json.html#ceiling-floor-and-abs) object.