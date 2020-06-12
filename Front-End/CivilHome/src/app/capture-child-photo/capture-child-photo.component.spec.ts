import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CaptureChildPhotoComponent } from './capture-child-photo.component';

describe('CaptureChildPhotoComponent', () => {
  let component: CaptureChildPhotoComponent;
  let fixture: ComponentFixture<CaptureChildPhotoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CaptureChildPhotoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CaptureChildPhotoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
