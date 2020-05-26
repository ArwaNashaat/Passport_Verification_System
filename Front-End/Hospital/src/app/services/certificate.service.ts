import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BirthCertificate } from '../info-page/info-page.component';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {
  Loading = false
  InvalidID_Name = false;
  constructor(private http:HttpClient) { }

  CreateCertificate(newCT : BirthCertificate){
    this.InvalidID_Name = false;
    this.Loading = true;
    let promise = new Promise((resolve, reject) => {

      this.http.post(`http://localhost:8080/Hospital/issueCertificate`, newCT)
        .toPromise()
        .then(
          res => {
            try {
              resolve(res);
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
