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
  ID: number;
  Address: string;
  Job: string;
  Gender: string;
  DOB: FormDataEntryValue;
  Photo: string;
  Religion: string
  Invalid: boolean;
  mStatus: string;
  Nationality: string
  image: any;
  newID: ID;
  image_name: String;

  constructor(private router: Router, public CreateUser: CreateUserService, private sharedImageService: ShareImageService,
    public rest: RestService, private LoginService: LoginServiceService ) {
    this.FullName = "";
    this.ID = 0;
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
    alert(this.image)
    if (this.FullName.length != 0 && this.ID &&
      this.Address.length != 0 && this.Job.length != 0 && this.Nationality.length != 0) {

      this.newID = new ID(this.ID.toString(), this.Address, this.FullName, this.Gender, this.Religion,
        this.Job, this.mStatus, this.Nationality, this.DOB.toString(),
        "0", false, this.image)
        const t = await this.CreateUser.CreateNewUser(this.newID)
        const v = await this.rest.CreateNewUser(this.newID)
        
      if (v && t) {
        alert("ID Created Successfully!")
        //this.router.navigate(['']) //should direct to the info page
      }
    }
    else {
      this.Invalid = true
    }
  }
}
