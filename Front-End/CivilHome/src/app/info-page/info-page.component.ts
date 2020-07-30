import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginServiceService } from '../services/login-service.service';

export class ID {
  constructor(
    public IDNumber: string,
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
    public personalPicture: any,
  ) {

  }
  
}

export class BirthCertificate{
  constructor(
    public  fullName : string,
    public  religion: string,
    public  gender : string,
    public  idNumber: string,
    public  dateOfBirth: string ,
    public  birthPlace:string,
    public  nationality:string,

    public fatherInfo: FatherInfo,

    public motherInfo: MotherInfo
  ){}
}

export class FatherInfo{
  constructor(
    public  fullName:string,

    public  nationality:string,

    public  religion:string

  ){}
}

export class MotherInfo{
  constructor(
    public  fullName:string,

    public  nationality:string,

    public  religion:string

  ){}
}

@Component({
  selector: 'app-info-page',
  templateUrl: './info-page.component.html',
  styleUrls: ['./info-page.component.css']
})
export class InfoPageComponent implements OnInit {

  constructor(private route : ActivatedRoute , private LoginService : LoginServiceService) { }
  ID:number
  myID : ID[]
  expired :boolean

  ngOnInit(): void {
    this.ID = this.route.snapshot.params['ID']
    this.myID = JSON.parse(sessionStorage.getItem("UserID"))
    this.expired = this.myID[0].isExpired
  }


  renew(){
    
  }
  

}
