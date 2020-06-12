import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BirthCertificate } from '../info-page/info-page.component';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  LoggedIn = false;
  birthCertificate : BirthCertificate
  constructor(private http: HttpClient) { }
  
  getBirthCertificate(parentIDNumber: string,childName: string) {
    
    return this.http.get<BirthCertificate>(`http://localhost:8080//Hospital/getBCByParentID/${parentIDNumber}/${childName}`)
  }

}
