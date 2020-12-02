import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvConverterUpdateComponent } from 'app/entities/rv-converter/rv-converter-update.component';
import { RvConverterService } from 'app/entities/rv-converter/rv-converter.service';
import { RvConverter } from 'app/shared/model/rv-converter.model';

describe('Component Tests', () => {
  describe('RvConverter Management Update Component', () => {
    let comp: RvConverterUpdateComponent;
    let fixture: ComponentFixture<RvConverterUpdateComponent>;
    let service: RvConverterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvConverterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RvConverterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RvConverterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RvConverterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RvConverter(123);
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
        const entity = new RvConverter();
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
