export const environment = {
  production: true,
  name: "production",
  messageServiceURLs: [{url: "mariadb/messages", source: "mariadb"},{url: "mongo/messages", source: "mongodb"}, {url:"cassandra/messages", source:"cassandra"}]
};
