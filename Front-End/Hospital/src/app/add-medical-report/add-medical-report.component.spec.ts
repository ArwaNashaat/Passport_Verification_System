import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMedicalReportComponent } from './add-medical-report.component';

describe('AddMedicalReportComponent', () => {
  let component: AddMedicalReportComponent;
  let fixture: ComponentFixture<AddMedicalReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddMedicalReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddMedicalReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
