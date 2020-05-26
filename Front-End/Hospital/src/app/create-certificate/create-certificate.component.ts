import { Component, OnInit } from '@angular/core';
import { BirthCertificate, MotherInfo, FatherInfo} from '../info-page/info-page.component';
import { Router } from '@angular/router';
import { CertificateService } from '../services/certificate.service';


@Component({
  selector: 'app-create-certificate',
  templateUrl: './create-certificate.component.html',
  styleUrls: ['./create-certificate.component.css']
})
export class CreateCertificateComponent implements OnInit {

  newCertficate:BirthCertificate;
  fatherInfo: FatherInfo;
  motherInfo: MotherInfo;
  FullName: string;
  ID: number;
  birthplace: string;
  Gender: string;
  DOB: FormDataEntryValue;
  Religion: string
  Invalid: boolean;
  Nationality: string;
  fatherNation:string;
  fatherReligion:string;
  fatherFullName:string;
  motherNation:string;
  motherReligion:string;
  motherFullName:string;
  constructor(private router : Router , public CreateCertificate:CertificateService) { 
    this.Invalid = false
    this.FullName = ""
    this.fatherFullName = ""
    this.motherFullName = ""
    this.Nationality = ""
    this.birthplace = ""
    this.motherNation=""
    this.fatherNation = ""
  }

  ngOnInit(): void {
  }
  async Create(){

    if (this.FullName.length != 0 && this.ID && this.fatherFullName.length != 0 && this.motherFullName.length != 0 && this.Nationality.length != 0
      &&this.birthplace.length !=0 && this.motherNation.length != 0 && this.fatherFullName.length !=0) {
     
      this.fatherInfo = new FatherInfo(this.fatherFullName, this.fatherNation, this.fatherReligion);
      this.motherInfo = new MotherInfo(this.motherFullName, this.motherNation, this.motherReligion);

      this.newCertficate = new BirthCertificate(this.FullName.toString(),this.Religion.toString(),this.Gender.toString(),this.ID.toString(),
      this.DOB.toString(),this.birthplace.toString(),this.Nationality.toString(), this.fatherInfo, this.motherInfo)
      
      const t =  await this.CreateCertificate.CreateCertificate(this.newCertficate)
      
      if (t) {
        alert("Birth Certificate Created Successfully!")
        this.CreateCertificate.Loading=false
        this.router.navigate([''])
      }
    }
    else {
      this.Invalid = true
    }
  }

}
