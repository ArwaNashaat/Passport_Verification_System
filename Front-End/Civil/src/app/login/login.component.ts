import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginServiceService } from '../services/login-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  Invalid =false;
  InvalidID = false
  ID:number
  Loading = false;
  constructor(private router: Router, private LoginService: LoginServiceService ) {
    
  }


  ngOnInit(): void {
  }

  async Login() {
    this.Loading = true

    if (this.ID) {
      let promise = new Promise((resolve, reject) => {

        this.LoginService.getInfo(this.ID)
          .toPromise()
          .then(
            res => {
              try {
                resolve(res);
                this.LoginService.SessionID = res
                this.LoginService.LoggedIn = true
                this.router.navigate(['Infopage/', this.ID])
              }
              catch (e) {
                reject(false);
              }
            },
            msg => {
              this.InvalidID = true
              this.Loading = false
              reject(msg);
            }
          );
      });
    }
    else {
      this.Invalid = true;
      this.Loading = false
    }
  }

}
