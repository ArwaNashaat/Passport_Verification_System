import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnterChildNameComponent } from './enter-child-name.component';

describe('EnterChildNameComponent', () => {
  let component: EnterChildNameComponent;
  let fixture: ComponentFixture<EnterChildNameComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnterChildNameComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnterChildNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
