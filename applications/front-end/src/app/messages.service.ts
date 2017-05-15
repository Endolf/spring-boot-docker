import { Injectable } from '@angular/core';
import uuid from 'uuid';
import { environment } from '../environments/environment';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import 'rxjs/add/observable/concat';
import 'rxjs/add/observable/merge';

@Injectable()
export class MessagesService {

  private messages: Message[] = [];

  constructor (private http: Http) {}

  public getMessagesForSource(url: string, source: string): Observable<Message[]> {
    return this.http.get(url)
      .map((response: Response) => {
        let body = response.json();
        console.log("Got data:", body);
        this.messages = this.messages.filter((value) => value.source!=source);
        this.messages = this.messages.concat((body._embedded.messages || []).map((value) => {
          value.source = source;
          let linkElements = value._links.self.href.split("/");
          value.id = linkElements[linkElements.length-1];
          return value;
        }));
        return this.messages;
      })
      .catch(this.handleError);
  }

  public getMessages(): Observable<Message[]> {
    let obs = environment.messageServiceURLs.map((serviceUrl) => this.getMessagesForSource(serviceUrl.url, serviceUrl.source));
    return Observable.merge.apply(this, obs);
  }

  private handleError (error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }
}

export class Message {
  public id: uuid;
  public timestamp: string;
  public message: string;
  public source: string;
}
