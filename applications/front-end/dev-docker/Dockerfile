FROM node:6.11-slim
VOLUME /tmp

RUN ["sh", "-c", "npm install -g @angular/cli"]

WORKDIR /development
ENTRYPOINT ["sh", "-c", "trap : TERM INT; ng serve --host `hostname -i`"]
EXPOSE 4200
