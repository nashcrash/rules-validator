import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvParamDetailComponent } from 'app/entities/rv-param/rv-param-detail.component';
import { RvParam } from 'app/shared/model/rv-param.model';

describe('Component Tests', () => {
  describe('RvParam Management Detail Component', () => {
    let comp: RvParamDetailComponent;
    let fixture: ComponentFixture<RvParamDetailComponent>;
    const route = ({ data: of({ rvParam: new RvParam(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvParamDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RvParamDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RvParamDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rvParam on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rvParam).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
