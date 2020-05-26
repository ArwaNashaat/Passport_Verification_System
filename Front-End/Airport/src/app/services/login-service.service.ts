import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  LoggedIn = false;
  constructor() { }
  Login(ID:number):boolean{
    if(ID){
      this.LoggedIn = true
      return true
    }
    return false;
  }
}
