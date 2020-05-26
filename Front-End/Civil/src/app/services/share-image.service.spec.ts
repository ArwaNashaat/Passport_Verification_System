import { TestBed } from '@angular/core/testing';

import { ShareImageService } from './share-image.service';

describe('ShareImageService', () => {
  let service: ShareImageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShareImageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
