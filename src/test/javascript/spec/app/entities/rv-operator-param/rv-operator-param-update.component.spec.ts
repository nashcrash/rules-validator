import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvOperatorParamUpdateComponent } from 'app/entities/rv-operator-param/rv-operator-param-update.component';
import { RvOperatorParamService } from 'app/entities/rv-operator-param/rv-operator-param.service';
import { RvOperatorParam } from 'app/shared/model/rv-operator-param.model';

describe('Component Tests', () => {
  describe('RvOperatorParam Management Update Component', () => {
    let comp: RvOperatorParamUpdateComponent;
    let fixture: ComponentFixture<RvOperatorParamUpdateComponent>;
    let service: RvOperatorParamService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvOperatorParamUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RvOperatorParamUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RvOperatorParamUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RvOperatorParamService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RvOperatorParam(123);
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
        const entity = new RvOperatorParam();
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
