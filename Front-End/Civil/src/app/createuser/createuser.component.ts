import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginServiceService } from '../services/login-service.service';
import { ID } from '../info-page/info-page.component';
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
  newID: ID

  constructor(private router: Router, public CreateUser: CreateUserService, private sharedImageService: ShareImageService,
    public rest: RestService, public LoginService: LoginServiceService) {
    this.FullName = "";
    this.ID = "0";
    this.Address = "";
    this.Job = ""
    this.Gender = "";
    this.DOB = "";
    this.image = this.sharedImageService.getImage();

  }

  ngOnInit(): void {
  }

  SharedImage: string;
  async Create() {
    if (this.FullName.length != 0 &&
      this.Address.length != 0 && this.Job.length != 0) {

      this.newID = new ID(this.Address, this.FullName, this.Gender, this.Religion,
        this.Job, this.mStatus,"Egyptian",this.DOB.toString(),
        "0", false, this.image)
        
        const t = await this.CreateUser.CreateNewUser(this.newID)
      if (t) {
        alert("ID Created Successfully!")

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
      this.LoginService.getInfo(this.ID)
        .toPromise()
        .then(
          res => {
            try {
              resolve(res)
              
              this.LoginService.SessionID = res
              this.LoginService.LoggedIn = true
              this.CreateUser.Loading = false
              this.router.navigate(['Infopage/', this.ID])
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
