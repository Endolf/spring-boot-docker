// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  name: "dev",

  // messageServiceURLs: [{url: "http://localhost:8090/messages", source: "mongodb"},{url: "http://172.17.0.1:8100/messages", source: "mariadb"}]
  messageServiceURLs: []
};
