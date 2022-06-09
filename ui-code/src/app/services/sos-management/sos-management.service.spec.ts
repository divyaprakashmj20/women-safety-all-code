import { TestBed } from '@angular/core/testing';

import { SosManagementService } from './sos-management.service';

describe('SosManagementService', () => {
  let service: SosManagementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SosManagementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
