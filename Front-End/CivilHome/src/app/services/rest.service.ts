import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ID } from '../info-page/info-page.component';
import { Router } from '@angular/router';
import { ShareImageService } from './share-image.service';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  Loading = false
  InvalidID_Name = false;
  idNumber: string

  constructor(private http: HttpClient , private router : Router , private sharedImage : ShareImageService) {

  }
  searchID(imageToSend: String) {
    const imageJson = {image: imageToSend};
  
    this.InvalidID_Name = false;
    this.Loading = true
    let promise = new Promise((resolve, reject) => {
     
      this.http.post(`http://127.0.0.1:8000/image`, imageJson)
        .toPromise()
        .then(
          res => {
            try {
              resolve(res);
              this.idNumber = res["image_name"];
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
