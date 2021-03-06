import { Component, OnInit } from '@angular/core';
import { ID } from '../info-page/info-page.component';
import { Router, ActivatedRoute } from '@angular/router';
import { CreateUserService } from '../services/create-user.service';
import { ShareImageService } from '../services/share-image.service';
import { RestService } from '../services/rest.service';
import { LoginServiceService } from '../services/login-service.service';

@Component({
  selector: 'app-renew-user',
  templateUrl: './renew-user.component.html',
  styleUrls: ['./renew-user.component.css']
})
export class RenewUserComponent implements OnInit {
  ID: string;
  Job: string;
  Gender: string;
  DOB: FormDataEntryValue;
  Photo: string;
  Invalid: boolean;
  Address:string;
  FullName:string
  mStatus: string;
  Religion:string;
  image: any;
  newID: ID;
  currentID: ID
  SharedImage: string;


  constructor(private route : ActivatedRoute, private router: Router, public CreateUser: CreateUserService, private sharedImageService: ShareImageService,
    public rest: RestService, public LoginService: LoginServiceService) {
    this.ID = this.rest.idNumber;
    this.Job = ""
    this.image = this.sharedImageService.getImage();
    this.currentID = this.LoginService.SessionID[0]
  }

  ngOnInit(): void {
  }

  async renewID() {
    if (this.Job.length != 0) {
      
      this.newID = new ID(this.ID,this.Address, this.FullName, this.currentID.gender, this.Religion,
        this.Job, this.mStatus,this.currentID.nationality,this.currentID.dateOfBirth,
        "0/0/0", false, this.image)

        const t = await this.CreateUser.renewUser(this.newID)
      if (t) {
        alert("ID Renewed Successfully!")
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

        this.LoginService.getInfo(this.ID)
          .toPromise()
          .then(
            res => {
              try {
                resolve(res)
                
                this.LoginService.SessionID = res
                this.LoginService.LoggedIn = true

                sessionStorage.setItem("UserID",JSON.stringify(res))
                this.router.navigate(['Infopage/', this.ID])
              }
              catch (e) {
                reject(false);
              }
            },
            msg => {
              reject(msg);
            }
          );
      });
  }
}
