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


const appRoutes: Routes = [

  { path: 'Infopage/:ID', component: InfoPageComponent },
  { path: 'CreateUser', component: CreateuserComponent },
  { path: '', component: CapturePhotoComponent}
  
];

@NgModule({
  declarations: [
    AppComponent,
    InfoPageComponent,
    NavbarComponent,
    CreateuserComponent,
    CapturePhotoComponent
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
