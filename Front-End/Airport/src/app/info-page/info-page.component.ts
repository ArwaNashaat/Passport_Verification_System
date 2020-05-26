import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-info-page',
  templateUrl: './info-page.component.html',
  styleUrls: ['./info-page.component.css']
})
export class InfoPageComponent implements OnInit {

  constructor(private route : ActivatedRoute) { }
  ID:number
  ngOnInit(): void {
    this.ID = this.route.snapshot.params['ID']
  }

  Notify(){
    alert("Hospital Notified");
  }

}
