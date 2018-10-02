import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { WebSocketSubject, webSocket } from 'rxjs/webSocket';

import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(
    private http: HttpClient
  ) { }

  list(): Observable<Object[]> {
    return this.http.get<Object[]>(environment.httpServerUrl);
  }

  create(obj: Object): Observable<Object> {
    return this.http.put(environment.httpServerUrl, obj);
  }

  listen(): WebSocketSubject<Object> {
    return webSocket<Object>(environment.wsServerUrl);
  }
}
