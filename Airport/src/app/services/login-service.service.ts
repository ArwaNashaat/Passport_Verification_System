import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  LoggedIn = false;
  constructor( private http : HttpClient) { }
  Login(ID:number){
    if(ID){
      this.http.get(`http://localhost:8080/Airport/getID/${ID}` ,{responseType:'text'})
        .subscribe(arg => this.LoggedIn = true)
    }
  }

}
