import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MicroscopePictureService } from 'app/entities/microscope-picture/microscope-picture.service';
import { IMicroscopePicture, MicroscopePicture } from 'app/shared/model/microscope-picture.model';

describe('Service Tests', () => {
  describe('MicroscopePicture Service', () => {
    let injector: TestBed;
    let service: MicroscopePictureService;
    let httpMock: HttpTestingController;
    let elemDefault: IMicroscopePicture;
    let expectedResult: IMicroscopePicture | IMicroscopePicture[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MicroscopePictureService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new MicroscopePicture(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MicroscopePicture', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new MicroscopePicture()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MicroscopePicture', () => {
        const returnedFromService = Object.assign(
          {
            path: 'BBBBBB',
            name: 'BBBBBB',
            desc: 'BBBBBB',
            order: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MicroscopePicture', () => {
        const returnedFromService = Object.assign(
          {
            path: 'BBBBBB',
            name: 'BBBBBB',
            desc: 'BBBBBB',
            order: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MicroscopePicture', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
