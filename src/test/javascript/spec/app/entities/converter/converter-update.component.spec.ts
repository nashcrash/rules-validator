import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ValidationRulesTestModule } from '../../../test.module';
import { ConverterUpdateComponent } from 'app/entities/converter/converter-update.component';
import { ConverterService } from 'app/entities/converter/converter.service';
import { Converter } from 'app/shared/model/converter.model';

describe('Component Tests', () => {
  describe('Converter Management Update Component', () => {
    let comp: ConverterUpdateComponent;
    let fixture: ComponentFixture<ConverterUpdateComponent>;
    let service: ConverterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ValidationRulesTestModule],
        declarations: [ConverterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConverterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConverterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConverterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Converter(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Converter();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
