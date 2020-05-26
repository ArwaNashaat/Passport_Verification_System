import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ShareImageService {
  public sharedImage:string;

  constructor(){
    this.sharedImage = "String from myService";
  }

  setImage (image) {
    this.sharedImage = image;
  }
  getImage () {
    return this.sharedImage;
  }
}
