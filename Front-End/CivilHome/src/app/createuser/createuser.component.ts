import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginServiceService } from '../services/login-service.service';
import { ID, BirthCertificate } from '../info-page/info-page.component';
import { CreateUserService } from '../services/create-user.service';
import { ShareImageService } from '../services/share-image.service';
import {RestService} from '../services/rest.service';

@Component({
  selector: 'app-createuser',
  templateUrl: './createuser.component.html',
  styleUrls: ['./createuser.component.css']
})
export class CreateuserComponent implements OnInit {
  FullName: string;
  ID: string;
  Address: string;
  Job: string;
  Gender: string;
  DOB: FormDataEntryValue;
  Photo: string;
  Religion: string
  Invalid: boolean;
  mStatus: string;
  image: any;
  newID: ID;
  birthCert: BirthCertificate;
  SharedImage: string;


  constructor(private router: Router, public CreateUser: CreateUserService, private sharedImageService: ShareImageService,
    public rest: RestService, public LoginService: LoginServiceService) {
    this.ID = "0";
    this.Address = "";
    this.Job = ""
    this.image = this.sharedImageService.getImage();
    this.birthCert = this.LoginService.birthCertificate
  }

  ngOnInit(): void {

  }

  async Create() {
    if (this.Address.length != 0 && this.Job.length != 0) {

      this.newID = new ID(this.ID,this.Address, this.birthCert.fullName, this.birthCert.gender, this.birthCert.religion,
        this.Job, this.mStatus,this.birthCert.nationality,this.birthCert.dateOfBirth,
        "0", false, this.image)
        
        const t = await this.CreateUser.CreateNewUser(this.newID)
      if (t) {
        alert("ID Created Successfully!")
        this.LoginService.LoggedIn = true;
        sessionStorage.setItem("UserID",JSON.stringify(this.newID))
        this.Login();
      }
    }
    else {
      this.Invalid = true
    }
  }

  async Login() {

    let promise = new Promise((resolve, reject) => {
      this.CreateUser.Loading = true
      this.LoginService.getInfo(this.CreateUser.idNumber)
        .toPromise()
        .then(
          res => {
            try {
              resolve(res)
              
              this.LoginService.SessionID = res
              this.LoginService.LoggedIn = true
              this.CreateUser.Loading = false
              this.router.navigate(['Infopage/', this.CreateUser.idNumber])
            }
            catch (e) {
              this.CreateUser.Loading = false
              reject(false);
            }
          },
          msg => {
            this.CreateUser.Loading = false
            reject(msg);
          }
        );
    });
    
  }

}
