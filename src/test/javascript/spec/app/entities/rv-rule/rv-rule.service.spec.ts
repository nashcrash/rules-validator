import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RvRuleService } from 'app/entities/rv-rule/rv-rule.service';
import { IRvRule, RvRule } from 'app/shared/model/rv-rule.model';
import { RvRuleMode } from 'app/shared/model/enumerations/rv-rule-mode.model';

describe('Service Tests', () => {
  describe('RvRule Service', () => {
    let injector: TestBed;
    let service: RvRuleService;
    let httpMock: HttpTestingController;
    let elemDefault: IRvRule;
    let expectedResult: IRvRule | IRvRule[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RvRuleService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new RvRule(0, 'AAAAAAA', 'AAAAAAA', RvRuleMode.FIRST_ERROR);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RvRule', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new RvRule()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RvRule', () => {
        const returnedFromService = Object.assign(
          {
            ruleCode: 'BBBBBB',
            description: 'BBBBBB',
            mode: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RvRule', () => {
        const returnedFromService = Object.assign(
          {
            ruleCode: 'BBBBBB',
            description: 'BBBBBB',
            mode: 'BBBBBB',
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

      it('should delete a RvRule', () => {
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
