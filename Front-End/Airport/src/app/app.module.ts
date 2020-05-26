import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';


import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { InfoPageComponent } from './info-page/info-page.component';
import { WebcamModule } from 'ngx-webcam';
import {CameraComponent} from './webcam/webcam.component';
import { ParentComponent } from './parent/parent/parent.component';
import { SibilingComponent } from './sibiling/sibiling.component';



const appRoutes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'Infopage/:ID', component: InfoPageComponent },
  {path: 'parent', component:ParentComponent},
  
];


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    InfoPageComponent,
    CameraComponent,
    ParentComponent,
    SibilingComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    WebcamModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
