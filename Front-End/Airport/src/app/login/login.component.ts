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
  ID:number
  constructor(private router: Router, private LoginService: LoginServiceService ) {
    
  }


  ngOnInit(): void {
  }

  Login() {
    if (this.LoginService.Login(this.ID)) {
      this.router.navigate(['Infopage/', this.ID])
      this.Invalid = false;
    }
    else{
      this.Invalid = true;
    }
  }

}
