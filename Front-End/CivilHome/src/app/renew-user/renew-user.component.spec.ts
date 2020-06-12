import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RenewUserComponent } from './renew-user.component';

describe('RenewUserComponent', () => {
  let component: RenewUserComponent;
  let fixture: ComponentFixture<RenewUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RenewUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RenewUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
