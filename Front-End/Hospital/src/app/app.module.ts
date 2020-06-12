import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';



import { AppComponent } from './app.component';
import { InfoPageComponent } from './info-page/info-page.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent} from './navbar/navbar.component';
import { AddMedicalReportComponent } from './add-medical-report/add-medical-report.component'
import { HttpClientModule } from '@angular/common/http';
import { CreateCertificateComponent } from './create-certificate/create-certificate.component';
import { CapturePhotoComponent } from './capture-photo/capture-photo.component';
import { ChildNameComponent } from './child-name/child-name.component';

const appRoutes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'Infopage/:ID', component: InfoPageComponent },
  { path: 'Report/:ID', component: AddMedicalReportComponent },
  { path: 'CreateCertificate', component: CreateCertificateComponent},
  { path: 'CapturePhoto', component: CapturePhotoComponent},
  { path: 'EnterChildName', component: ChildNameComponent}
  
  
];

@NgModule({
  declarations: [
    AppComponent,
    InfoPageComponent,
    LoginComponent,
    NavbarComponent,
    AddMedicalReportComponent,
    CreateCertificateComponent,
    CapturePhotoComponent,
    ChildNameComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
