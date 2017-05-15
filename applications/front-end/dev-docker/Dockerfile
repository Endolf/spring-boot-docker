FROM node:6.10
VOLUME /tmp

RUN ["sh", "-c", "npm install -g @angular/cli"]

WORKDIR /development
ENTRYPOINT ["sh", "-c", "ng serve --host `hostname -i`"]
EXPOSE 4200
