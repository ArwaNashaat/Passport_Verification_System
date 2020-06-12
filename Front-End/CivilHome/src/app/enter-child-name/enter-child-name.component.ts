import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginServiceService } from '../services/login-service.service';
import { RestService } from '../services/rest.service';

@Component({
  selector: 'app-enter-child-name',
  templateUrl: './enter-child-name.component.html',
  styleUrls: ['./enter-child-name.component.css']
})

export class EnterChildNameComponent implements OnInit {
  Invalid = false;
  childName: string
  InvalidID = false;
  Loading = false
  constructor(private router: Router, private LoginService: LoginServiceService, 
    private rest: RestService) {

  }

  ngOnInit(): void {
  }

  async Login() {
    this.Loading = true

    if (this.childName) {
      let promise = new Promise((resolve, reject) => {
        
        this.LoginService.getBirthCertificate(this.rest.idNumber,this.childName)
        
          .toPromise()
          .then(
            res => {
              try {
                resolve(res);
                this.LoginService.birthCertificate = res
                this.LoginService.LoggedIn = true
                
                this.router.navigate(['CaptureChildPhoto'])
                this.Loading = false;
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
  checkLogin() {
    alert("true")
    alert(this.router.toString());  
  }
}
