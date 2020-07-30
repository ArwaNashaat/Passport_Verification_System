import { Component, OnInit } from '@angular/core';
import { BirthCertificate, MotherInfo, FatherInfo } from '../info-page/info-page.component';
import { Router } from '@angular/router';
import { CertificateService } from '../services/certificate.service';
import { LoginComponent } from '../login/login.component';
import { LoginServiceService } from '../services/login-service.service';


@Component({
  selector: 'app-create-certificate',
  templateUrl: './create-certificate.component.html',
  styleUrls: ['./create-certificate.component.css']
})
export class CreateCertificateComponent implements OnInit {

  newCertficate: BirthCertificate;
  fatherInfo: FatherInfo;
  motherInfo: MotherInfo;
  FullName: string;
  ID: string;
  birthplace: string;
  Gender: string;
  DOB: FormDataEntryValue;
  Religion: string
  Invalid: boolean;
  Nationality: string;
  fatherNation: string;
  fatherReligion: string;
  fatherFullName: string;
  motherNation: string;
  motherReligion: string;
  motherFullName: string;
  ErrMessage = ""
  constructor(private router: Router, public CreateCertificate: CertificateService, public LoginService: LoginServiceService) {
    this.Invalid = false
    this.FullName = ""
    this.ID = "0"
    this.fatherFullName = ""
    this.motherFullName = ""
    this.Nationality = ""
    this.birthplace = ""
    this.motherNation = ""
    this.fatherNation = ""
  }

  ngOnInit(): void {
    this.ErrMessage = ""
    this.Invalid= false
  }
  async Create() {
    this.Invalid = false;
    if ( this.FullName.match("^[a-zA-Z]{2,}(?: [a-zA-Z--]+){3}$")== null) {
      this.ErrMessage = "Please Enter Valid FullName"
      this.Invalid = true
      return
    }
    if ( this.fatherFullName.match("^[a-zA-Z]{2,}(?: [a-zA-Z--]+){3}$")==null) {
      this.ErrMessage = "Please Enter Valid Father's FullName"
      this.Invalid = true
      return
    }
    if (this.motherFullName.match("^[a-zA-Z]{2,}(?: [a-zA-Z--]+){3}$")==null) {
      this.ErrMessage = "Please Enter Valid Mother's FullName"
      this.Invalid = true
      return
    }
    if (this.Nationality.length == 0) {
      this.ErrMessage = "Please Enter Valid Nationality"
      this.Invalid = true
      return
    }
    if (this.birthplace.length == 0) {
      this.ErrMessage = "Please Enter Valid Birtplace"
      this.Invalid = true
      return
    }
    

    this.fatherInfo = new FatherInfo(this.fatherFullName, this.fatherNation, this.fatherReligion);
    this.motherInfo = new MotherInfo(this.motherFullName, this.motherNation, this.motherReligion);

    this.newCertficate = new BirthCertificate(this.FullName.toString(), this.Religion.toString(), this.Gender.toString(), this.ID.toString(),
      this.DOB.toString(), this.birthplace.toString(), this.Nationality.toString(), this.fatherInfo, this.motherInfo)

    const t = await this.CreateCertificate.CreateCertificate(this.newCertficate)

    if (t) {
      alert("Birth Certificate Created Successfully!")
      this.CreateCertificate.Loading = false

      //this.router.navigate(['Infopage/'])
    }
    this.CreateCertificate.Loading=false;
  }

}
