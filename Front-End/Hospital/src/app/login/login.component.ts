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
  ID:string
  InvalidID = false;
  Loading = false;
  
  constructor(private router: Router, private LoginService: LoginServiceService ) {
    
  }


  ngOnInit(): void {
  }

 
}
