# NGS2-Router

NGS2-Router is a webservice which provide capabilities to control traffic of incoming participants until it meets minimum condition that satisfies available experiments and route participants into randomly selected experiment or experiment site. 

## Start the Router

1. Open a terminal and `cd server`
2. Execute `sbt 'run 9090'`
3. Go to http://localhost:9090
4. Log in with `admin1@demo.com` and the password `password`

## Create and connect to a test Experiment Instance

1. Create an Experiment Instance with the URL: `https://brdbrd.net:9776/game/417/9253/{id}/connected`
2. Set `# Participants` to '3'
3. Connect to `http://localhost:9090/client/ABCD1234`, `http://localhost:9090/client/EFGH5678`, and `http://localhost:9090/client/IJKL2468`

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
