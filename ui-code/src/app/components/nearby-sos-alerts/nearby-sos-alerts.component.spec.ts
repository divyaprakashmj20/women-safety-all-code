import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NearbySosAlertsComponent } from './nearby-sos-alerts.component';

describe('NearbySosAlertsComponent', () => {
  let component: NearbySosAlertsComponent;
  let fixture: ComponentFixture<NearbySosAlertsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NearbySosAlertsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NearbySosAlertsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
