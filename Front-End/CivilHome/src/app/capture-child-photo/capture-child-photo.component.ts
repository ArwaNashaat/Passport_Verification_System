import { Component, OnInit, Renderer2, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { CreateUserService } from '../services/create-user.service';
import { RestService } from '../services/rest.service';
import { ShareImageService } from '../services/share-image.service';
import { LoginServiceService } from '../services/login-service.service';

@Component({
  selector: 'app-capture-child-photo',
  templateUrl: './capture-child-photo.component.html',
  styleUrls: ['./capture-child-photo.component.css']
})
export class CaptureChildPhotoComponent implements OnInit {
  videoWidth = 0;
  videoHeight = 0;
  public image: string;
  Invalid =false;
  InvalidID = false
  ID:number
  
  Loading = false;

  constructor(private router :Router, public CreateUser: CreateUserService, public rest: RestService,private renderer: Renderer2, private sharedImageService: ShareImageService, private LoginService: LoginServiceService) { 
    this.image = "";
  }
  
  @ViewChild('video', { static: true }) videoElement: ElementRef;
  @ViewChild('canvas', { static: true }) canvas: ElementRef;

  ngOnInit() {
    this.startCamera();
    this.sharedImageService.setImage("");
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
  
  nextPage(){
    this.stopCamera();
    this.router.navigate(['CreateUser'])
  }
  stopCamera(){
    navigator.mediaDevices.getUserMedia(this.constraints).then(stop);
  }

}
