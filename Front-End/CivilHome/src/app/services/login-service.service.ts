import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ID } from '../info-page/info-page.component';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  LoggedIn = false;
  SessionID : ID
  constructor(private http: HttpClient) { }
  
  getInfo(ID: number) {
    return this.http.get<ID>(`http://localhost:8080/Airport/getInfo/${ID}`)
  }
  
}
