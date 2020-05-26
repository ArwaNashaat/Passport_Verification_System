import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginServiceService } from '../services/login-service.service';


export class ID {
  constructor(
    public number: string,
    public address: string,
    public fullName: string,
    public gender: string,
    public religion: string,
    public job: string,
    public maritalStatus: string,
    public nationality: string,
    public dateOfBirth: string,
    public expireDate: string,
    public isExpired: boolean,
  ) {

  }
}

@Component({
  selector: 'app-info-page',
  templateUrl: './info-page.component.html',
  styleUrls: ['./info-page.component.css']
})
export class InfoPageComponent implements OnInit {

  constructor(private route: ActivatedRoute , private LoginService : LoginServiceService) { }
  ID: number
  myID: ID
  ngOnInit(): void {

    this.ID = this.route.snapshot.params['ID']
    this.myID = this.LoginService.SessionID
  }
  
  AddVisa() {
    alert("Visa Created");
  }

}
