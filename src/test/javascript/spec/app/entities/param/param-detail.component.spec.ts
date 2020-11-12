import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { ParamDetailComponent } from 'app/entities/param/param-detail.component';
import { Param } from 'app/shared/model/param.model';

describe('Component Tests', () => {
  describe('Param Management Detail Component', () => {
    let comp: ParamDetailComponent;
    let fixture: ComponentFixture<ParamDetailComponent>;
    const route = ({ data: of({ param: new Param(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [ParamDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ParamDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParamDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load param on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.param).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
