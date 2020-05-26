import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-medical-report',
  templateUrl: './add-medical-report.component.html',
  styleUrls: ['./add-medical-report.component.css']
})
export class AddMedicalReportComponent implements OnInit {

  constructor(private route :ActivatedRoute , private router : Router) { }
  ID : number;
  DoctorID:number
  Description:string
  ngOnInit(): void {
    this.ID = this.route.snapshot.params['ID']
  }
  Create(){
    
  }
  Cancel(){
    this.router.navigate(['Infopage/', this.ID])
  }

}
