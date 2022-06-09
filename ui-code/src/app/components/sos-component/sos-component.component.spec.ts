import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SosComponentComponent } from './sos-component.component';

describe('SosComponentComponent', () => {
  let component: SosComponentComponent;
  let fixture: ComponentFixture<SosComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SosComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SosComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
