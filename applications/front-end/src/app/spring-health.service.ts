import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {Http, Response} from "@angular/http";

@Injectable()
export class SpringHealthService {

  constructor(private http: Http) {}

  public getSpringHealth(url: string): Observable<SpringHealthAggregate> {
    return this.http.get(url)
      .map(SpringHealthAggregate.convertFromResponse)
      .catch(this.handleError);
  }

  private handleError(error: Response | any) {
    let errMsg;
    if (error instanceof Response) {
      if(error.status===503) {
        return Observable.from([SpringHealthAggregate.convertFromResponse(error)]);
      } else {
        errMsg = `${error.status} - ${error.statusText || ''} ${error}`;
      }
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    return Observable.throw(errMsg);
  }
}

export class SpringHealthAggregate {
  constructor(public status, public healthItems: SpringHealth[]) {}

  public static convertFromResponse(response: Response): SpringHealthAggregate {
    let body = response.json();
    return new SpringHealthAggregate(body.status,
      Object.getOwnPropertyNames(body)
        .filter(name => name != "status")
        .map(healthItem => new SpringHealth(body[healthItem].status, healthItem,
          Object.getOwnPropertyNames(body[healthItem])
            .filter(name => name != "status")
            .filter(name => name != "error")
            .map(detail => new SpringHealthDetail(detail, body[healthItem][detail])))));
  }
}

export class SpringHealth {
  constructor(public status, public name, public details: SpringHealthDetail[]) {}
}

export class SpringHealthDetail {
  constructor(public name: string, public value: string) {
  }
}
