import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { LoginServiceService } from '../services/login-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})


export class LoginComponent implements OnInit {
  Invalid =false;
  InvalidID = false
  ID:number
  Loading = false;
  constructor(private router: Router, private LoginService: LoginServiceService ) {
    
  }

@ViewChild('canvas', { static: true }) canvas: ElementRef;

  ngOnInit(): void {
  }

  async Login() {
    this.Loading = true

    if (this.ID) {
      let promise = new Promise((resolve, reject) => {

        this.LoginService.getInfo(this.ID)
          .toPromise()
          .then(
            res => {
              try {
                alert("before")
                resolve(res)
                alert(res.personalPicture) 
                alert("after")
                this.LoginService.SessionID = res
                this.LoginService.LoggedIn = true
                this.router.navigate(['Infopage/', this.ID])
              }
              catch (e) {
                reject(false);
              }
            },
            msg => {
              this.InvalidID = true
              this.Loading = false
              reject(msg);
            }
          );
      });
    }
    else {
      this.Invalid = true;
      this.Loading = false
    }
  }
  getBase64Image(img) {
    alert(img)
    
    this.canvas.nativeElement.width = img.width;
    this.canvas.nativeElement.height = img.height;
    var ctx = this.canvas.nativeElement.getContext("2d").drawImage(img, 0, 0);
    alert("after")
    alert(this.canvas.nativeElement.toDataURL());
    }
  
}
