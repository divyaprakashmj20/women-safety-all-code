import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SosTestComponent } from './sos-test.component';

describe('SosTestComponent', () => {
  let component: SosTestComponent;
  let fixture: ComponentFixture<SosTestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SosTestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SosTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
