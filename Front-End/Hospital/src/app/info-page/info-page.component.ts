import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginServiceService } from '../services/login-service.service';


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

  constructor(private route : ActivatedRoute , private LoginService : LoginServiceService , private router : Router) { }
  ID:number
  birthCertificate : BirthCertificate
  fatherInfo : FatherInfo
  motherInfo : MotherInfo
  ngOnInit(): void {
    
    this.ID = this.route.snapshot.params['ID']
    this.birthCertificate = this.LoginService.birthCertificate
    this.fatherInfo = this.birthCertificate.fatherInfo
    this.motherInfo = this.birthCertificate.motherInfo
  }
  
  AddReport(){
    this.router.navigate(['Report/', this.ID]);
  }

}