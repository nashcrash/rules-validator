import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { ParamUpdateComponent } from 'app/entities/param/param-update.component';
import { ParamService } from 'app/entities/param/param.service';
import { Param } from 'app/shared/model/param.model';

describe('Component Tests', () => {
  describe('Param Management Update Component', () => {
    let comp: ParamUpdateComponent;
    let fixture: ComponentFixture<ParamUpdateComponent>;
    let service: ParamService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [ParamUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ParamUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParamUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParamService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Param(123);
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
        const entity = new Param();
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
