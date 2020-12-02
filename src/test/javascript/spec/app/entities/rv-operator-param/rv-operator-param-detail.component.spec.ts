import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvOperatorParamDetailComponent } from 'app/entities/rv-operator-param/rv-operator-param-detail.component';
import { RvOperatorParam } from 'app/shared/model/rv-operator-param.model';

describe('Component Tests', () => {
  describe('RvOperatorParam Management Detail Component', () => {
    let comp: RvOperatorParamDetailComponent;
    let fixture: ComponentFixture<RvOperatorParamDetailComponent>;
    const route = ({ data: of({ rvOperatorParam: new RvOperatorParam(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvOperatorParamDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RvOperatorParamDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RvOperatorParamDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rvOperatorParam on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rvOperatorParam).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
