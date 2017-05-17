import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpringHealthComponent } from './spring-health.component';

describe('SpringHealthComponent', () => {
  let component: SpringHealthComponent;
  let fixture: ComponentFixture<SpringHealthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpringHealthComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpringHealthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
