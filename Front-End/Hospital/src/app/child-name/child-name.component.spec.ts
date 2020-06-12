import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChildNameComponent } from './child-name.component';

describe('ChildNameComponent', () => {
  let component: ChildNameComponent;
  let fixture: ComponentFixture<ChildNameComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChildNameComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChildNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
