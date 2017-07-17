# NGS2-Router

NGS2-Router is a webservice which provide capabilities to control traffic of incoming participants until it meets minimum condition that satisfies available experiments and route participants into randomly selected experiment or experiment site. 

## Steps to run client and server

Change directory to client folder in side the project and execute `grunt serve`. Client will be running on `port 9000` while listening to the server on `port 9090`

```
Running "configureProxies:server" (configureProxies) task
Proxy created for: /app to localhost:9090

Running "postcss:server" (postcss) task
>> 1 processed stylesheet created.

Running "connect:livereload" (connect) task
Started connect web server on http://localhost:9000

Running "watch" task
Waiting...
```
Now change the directory `server` directory in the project folder and execute `sbt` command and then `run 9090`.  

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
