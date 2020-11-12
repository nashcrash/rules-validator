import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { ConverterDetailComponent } from 'app/entities/converter/converter-detail.component';
import { Converter } from 'app/shared/model/converter.model';

describe('Component Tests', () => {
  describe('Converter Management Detail Component', () => {
    let comp: ConverterDetailComponent;
    let fixture: ComponentFixture<ConverterDetailComponent>;
    const route = ({ data: of({ converter: new Converter(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [ConverterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConverterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConverterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load converter on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.converter).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
