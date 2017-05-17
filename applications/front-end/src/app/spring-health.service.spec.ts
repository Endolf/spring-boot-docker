import { TestBed, inject } from '@angular/core/testing';

import { SpringHealthService } from './spring-health.service';

describe('SpringHealthService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpringHealthService]
    });
  });

  it('should be created', inject([SpringHealthService], (service: SpringHealthService) => {
    expect(service).toBeTruthy();
  }));
});
