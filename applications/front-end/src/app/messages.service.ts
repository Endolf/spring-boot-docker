import { Injectable } from '@angular/core';
import uuid from 'uuid';

@Injectable()
export class MessagesService {

  constructor() { }

  public getMessages(): Message[] {
    return [
      {
        source: "mongo",
        timestamp : "2017-05-13T15:50:54.502Z",
        message : "Application started: \"Core test application\" at 1494690654500 on orion",
        id : "8c58fa8c-b996-4939-9312-221bda3cbb01"
      }, {
        source: "mongo",
        timestamp : "2017-05-13T15:51:14.199Z",
        message : "Application started: \"Mongo test application\" at 1494690674197 on orion",
        id : "4fc51560-2978-4ea8-81de-3d904c4187da"
      }, {
        source: "mongo",
        timestamp : "2017-05-13T15:58:16.271Z",
        message : "Application started: \"Mongo test application\" at 1494691096269 on orion",
        id : "bb85d2f7-26a2-4131-b104-7a093650a3f9"
      }, {
        source: "mongo",
        timestamp : "2017-05-15T16:49:13.583Z",
        message : "Application started: \"Mariadb test application\" at 1494866953581 on orion",
        id : "41ae3d20-d6db-4e1c-a8ee-eb6899b3a6cb"
      },
      {
        source: "mariadb",
        timestamp : "2017-05-13T15:50:54.502Z",
        message : "Application started: \"Core test application\" at 1494690654500 on orion",
        id : "8c58fa8c-b996-4939-9312-221bda3cbb01"
      }, {
        source: "mariadb",
        timestamp : "2017-05-13T15:51:14.199Z",
        message : "Application started: \"Mongo test application\" at 1494690674197 on orion",
        id : "4fc51560-2978-4ea8-81de-3d904c4187da"
      }, {
        source: "mariadb",
        timestamp : "2017-05-13T15:58:16.271Z",
        message : "Application started: \"Mongo test application\" at 1494691096269 on orion",
        id : "bb85d2f7-26a2-4131-b104-7a093650a3f9"
      }, {
        source: "mariadb",
        timestamp : "2017-05-15T16:49:13.583Z",
        message : "Application started: \"Mariadb test application\" at 1494866953581 on orion",
        id : "41ae3d20-d6db-4e1c-a8ee-eb6899b3a6cb"
      }
    ];
  }
}

export class Message {
  public id: uuid;
  public timestamp: string;
  public message: string;
  public source: string;
}
