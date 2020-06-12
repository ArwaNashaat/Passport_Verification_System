import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ID, BirthCertificate } from '../info-page/info-page.component';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  LoggedIn = false;
  SessionID : ID
  birthCertificate : BirthCertificate
  constructor(private http: HttpClient) { }
  
  getBirthCertificate(parentIDNumber: string,childName: string) {
    
    return this.http.get<BirthCertificate>(`http://localhost:8080//Hospital/getBCByParentID/${parentIDNumber}/${childName}`)
  }

  getInfo(IDNumber: string) {
    return this.http.get<ID>(`http://localhost:8080/Airport/getInfo/${IDNumber}`)
  }

}
