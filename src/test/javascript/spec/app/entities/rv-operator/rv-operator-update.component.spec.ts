import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvOperatorUpdateComponent } from 'app/entities/rv-operator/rv-operator-update.component';
import { RvOperatorService } from 'app/entities/rv-operator/rv-operator.service';
import { RvOperator } from 'app/shared/model/rv-operator.model';

describe('Component Tests', () => {
  describe('RvOperator Management Update Component', () => {
    let comp: RvOperatorUpdateComponent;
    let fixture: ComponentFixture<RvOperatorUpdateComponent>;
    let service: RvOperatorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvOperatorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RvOperatorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RvOperatorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RvOperatorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RvOperator(123);
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
        const entity = new RvOperator();
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
