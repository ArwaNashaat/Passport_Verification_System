import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ID } from '../info-page/info-page.component';
import { Router } from '@angular/router';
import { ShareImageService } from './share-image.service';

@Injectable({
  providedIn: 'root'
})
export class CreateUserService {
  idNumber: string
  Loading = false
  InvalidID_Name = false;
  constructor(private http: HttpClient , private router : Router , private sharedImage : ShareImageService) {

  }
  CreateNewUser(newID: ID) {
    this.Loading = true
    let promise = new Promise((resolve, reject) => {

      this.http.post(`http://localhost:8080/Airport/IssueID`, newID)
        .toPromise()
        .then(
          res => {
            try {
              resolve(res); 
              this.idNumber = res.toString()
              this.Loading = false
            }
            catch (e) {
              reject(false);
            }
          },
          msg => {
            this.InvalidID_Name = true;
            this.Loading = false
            reject(msg);
          }
        );
    });
    return promise;
  }

  renewUser(currnetID:string, job:string, maritalStatus:string) {
    this.Loading = true
    let promise = new Promise((resolve, reject) => {
    
        //return this.http.get<BirthCertificate>(`http://localhost:8080//Hospital/getBCByParentID/${parentIDNumber}/${childName}`)
        
      this.http.post(`http://localhost:8080/Airport/renewID`, {currnetID,job,maritalStatus})
        .toPromise()
        .then(
          res => {
            try {
              resolve(res); 
              this.idNumber = res.toString()
              this.Loading = false
            }
            catch (e) {
              reject(false);
            }
          },
          msg => {
            this.InvalidID_Name = true;
            this.Loading = false
            reject(msg);
          }
        );
    });
    return promise;
  }
}
