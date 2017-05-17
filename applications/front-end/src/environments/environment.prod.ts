export const environment = {
  production: true,
  name: "production",
  messageServiceURLs: [{url: "mariadb/messages", source: "mariadb", healthUrl: "mariadb/health"}
                      ,{url: "mongo/messages", source: "mongodb", healthUrl: "mongo/health"}
                      ,{url: "cassandra/messages", source:"cassandra", healthUrl: "cassandra/health"}]
};
