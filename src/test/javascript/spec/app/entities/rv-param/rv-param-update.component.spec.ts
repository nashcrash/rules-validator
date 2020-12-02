import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvParamUpdateComponent } from 'app/entities/rv-param/rv-param-update.component';
import { RvParamService } from 'app/entities/rv-param/rv-param.service';
import { RvParam } from 'app/shared/model/rv-param.model';

describe('Component Tests', () => {
  describe('RvParam Management Update Component', () => {
    let comp: RvParamUpdateComponent;
    let fixture: ComponentFixture<RvParamUpdateComponent>;
    let service: RvParamService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvParamUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RvParamUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RvParamUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RvParamService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RvParam(123);
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
        const entity = new RvParam();
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
