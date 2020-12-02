import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvConverterDetailComponent } from 'app/entities/rv-converter/rv-converter-detail.component';
import { RvConverter } from 'app/shared/model/rv-converter.model';

describe('Component Tests', () => {
  describe('RvConverter Management Detail Component', () => {
    let comp: RvConverterDetailComponent;
    let fixture: ComponentFixture<RvConverterDetailComponent>;
    const route = ({ data: of({ rvConverter: new RvConverter(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvConverterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RvConverterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RvConverterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rvConverter on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rvConverter).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
