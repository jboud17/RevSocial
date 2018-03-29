import { TestBed, inject } from '@angular/core/testing';

import { AllPostsService } from './all-posts.service';

describe('AllPostsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AllPostsService]
    });
  });

  it('should be created', inject([AllPostsService], (service: AllPostsService) => {
    expect(service).toBeTruthy();
  }));
});
