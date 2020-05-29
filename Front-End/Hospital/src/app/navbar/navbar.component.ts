import { Component, OnInit } from '@angular/core';
import { LoginServiceService } from '../services/login-service.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  constructor(public LoginService : LoginServiceService) { 
  }

  ngOnInit(): void {
  }
  SignOut(){
    this.LoginService.birthCertificate = null
    this.LoginService.LoggedIn = false;
  }

}
