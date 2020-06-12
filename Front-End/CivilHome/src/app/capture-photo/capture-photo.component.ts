import { Component, OnInit, ViewChild, ElementRef, Renderer2} from '@angular/core';
import { Router } from '@angular/router';
import {ShareImageService} from '../services/share-image.service';
import { LoginServiceService } from '../services/login-service.service';
import { RestService } from '../services/rest.service';
import { CreateUserService } from '../services/create-user.service';


@Component({
  selector: 'app-capture-photo',
  templateUrl: './capture-photo.component.html',
  styleUrls: ['./capture-photo.component.css']
})


export class CapturePhotoComponent implements OnInit {

  videoWidth = 0;
  videoHeight = 0;
  public image: string;
  Invalid =false;
  InvalidID = false
  ID:number
  
  Loading = false;
  LoadingRenew = false;

  constructor(private router :Router, public CreateUser: CreateUserService, public rest: RestService,private renderer: Renderer2, private sharedImageService: ShareImageService, private LoginService: LoginServiceService) { 
    this.image = "";
  }
  
  @ViewChild('video', { static: true }) videoElement: ElementRef;
  @ViewChild('canvas', { static: true }) canvas: ElementRef;

  ngOnInit() {
    this.startCamera();
  }

  constraints = {
    video: {
        facingMode: "environment",
        width: { ideal: 1200 },
        height: { ideal: 1000 }
    }
  };

  startCamera() {
    if (!!(navigator.mediaDevices && navigator.mediaDevices.getUserMedia)) { 
        navigator.mediaDevices.getUserMedia(this.constraints).then(this.attachVideo.bind(this)).catch(this.handleError);
    
      } else {
        alert('Sorry, camera not available.');
    }
  }
  handleError(error) {
    console.log('Error: ', error);
  }

  attachVideo(stream) {
    this.renderer.setProperty(this.videoElement.nativeElement, 'srcObject', stream);
    this.renderer.listen(this.videoElement.nativeElement, 'play', (event) => {
        this.videoHeight = this.videoElement.nativeElement.videoHeight;
        this.videoWidth = this.videoElement.nativeElement.videoWidth;
      });
  
  }
  
  capture() {
    this.renderer.setProperty(this.canvas.nativeElement, 'width', this.videoWidth);
    this.renderer.setProperty(this.canvas.nativeElement, 'height', this.videoHeight);
    this.canvas.nativeElement.getContext('2d').drawImage(this.videoElement.nativeElement, 0, 0);
    this.image = (this.canvas.nativeElement.toDataURL("data:image/png;base64"));
    this.sharedImageService.setImage(this.image);
  }
  
  stopCamera(){
    var vid = document.getElementById("vid");
    navigator.mediaDevices.getUserMedia(this.constraints).then((track => track.removeTrack));

    //navigator.mediaDevices.getUserMedia(this.constraints).then(stop);
  }
  
  async searchID(){
    this.stopCamera()
    if (this.image != "") {
      this.Loading = true
        const t = await this.rest.searchID(this.image)
        this.Loading = false
      if (t && this.rest.idNumber==="No Match") {
        alert("No ID Found")
      }
      else{
        alert("ID Confirmed")
        this.router.navigate(['EnterChildName'])
      }
    }
    else {
      alert("Please Capture your Photo First")
      this.Invalid = true
    }
  }
  async renewID(){
    if (this.image != "") {
      this.LoadingRenew = true
        const t = await this.rest.searchID(this.image)
        this.LoadingRenew = false
      if (t) {
        this.Login(this.rest.idNumber)
      }
    }
    else {
      this.Invalid = true
    }
  }
  
  async Login(idNumber: string) {
    this.LoadingRenew = true

    if (idNumber) {
      let promise = new Promise((resolve, reject) => {

        this.LoginService.getInfo(idNumber)
          .toPromise()
          .then(
            res => {
              try {
                resolve(res)                
                this.LoginService.SessionID = res
                this.LoginService.LoggedIn = true
                sessionStorage.setItem("UserID",JSON.stringify(res))
                alert("ID Confirmed")
                this.router.navigate(['RenewUser'])
              }
              catch (e) {
                reject(false);
              }
            },
            msg => {
              this.InvalidID = true
              this.LoadingRenew = false
              reject(msg);
            }
          );
      });
    }
    else {
      this.Invalid = true;
      this.LoadingRenew = false
    }
  }
}
