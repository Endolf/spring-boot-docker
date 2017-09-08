FROM node:6.10-alpine
VOLUME /tmp

RUN ["sh", "-c", "npm install -g @angular/cli"]
RUN ["sh", "-c", "npm install -g node-sass"]

WORKDIR /development
ENTRYPOINT ["sh", "-c", "trap : TERM INT; ng serve --host `hostname -i`"]
EXPOSE 4200
