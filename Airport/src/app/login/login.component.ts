import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginServiceService } from '../services/login-service.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  Invalid = false;
  ID: number
  LoginGranted = false;
  InvalidID = false;
  StaticAlertClose = false
  constructor(private router: Router, private LoginService: LoginServiceService) {

  }


  ngOnInit(): void {
  }

  async Login() {
    if (this.ID) {
      const t = await this.LoginService.Login(this.ID)
      if (t) {
        this.router.navigate(['Infopage/', this.ID])
        this.Invalid = false;
      }
      else{
        this.InvalidID = true;
      }
    }
    else {
      this.Invalid = true;
    }
  }

}
