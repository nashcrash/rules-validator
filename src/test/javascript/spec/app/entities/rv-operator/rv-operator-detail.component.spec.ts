import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvOperatorDetailComponent } from 'app/entities/rv-operator/rv-operator-detail.component';
import { RvOperator } from 'app/shared/model/rv-operator.model';

describe('Component Tests', () => {
  describe('RvOperator Management Detail Component', () => {
    let comp: RvOperatorDetailComponent;
    let fixture: ComponentFixture<RvOperatorDetailComponent>;
    const route = ({ data: of({ rvOperator: new RvOperator(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvOperatorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RvOperatorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RvOperatorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rvOperator on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rvOperator).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
