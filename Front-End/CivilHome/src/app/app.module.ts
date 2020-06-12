import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { InfoPageComponent } from './info-page/info-page.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CreateuserComponent } from './createuser/createuser.component';
import { HttpClientModule } from '@angular/common/http';
import { CapturePhotoComponent } from './capture-photo/capture-photo.component';
import {ShareImageService} from './services/share-image.service';
import { EnterChildNameComponent } from './enter-child-name/enter-child-name.component';
import { CaptureChildPhotoComponent } from './capture-child-photo/capture-child-photo.component';
import { RenewUserComponent } from './renew-user/renew-user.component';


const appRoutes: Routes = [

  { path: 'Infopage/:ID', component: InfoPageComponent },
  { path: 'CreateUser', component: CreateuserComponent },
  { path: 'EnterChildName', component: EnterChildNameComponent },
  { path: 'CaptureChildPhoto', component: CaptureChildPhotoComponent },
  { path: 'RenewUser', component: RenewUserComponent },
  { path: '', component: CapturePhotoComponent}
  
];

@NgModule({
  declarations: [
    AppComponent,
    InfoPageComponent,
    NavbarComponent,
    CreateuserComponent,
    CapturePhotoComponent,
    EnterChildNameComponent,
    CaptureChildPhotoComponent,
    RenewUserComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
  ],
  providers: [ShareImageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
