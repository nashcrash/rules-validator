import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvRuleUpdateComponent } from 'app/entities/rv-rule/rv-rule-update.component';
import { RvRuleService } from 'app/entities/rv-rule/rv-rule.service';
import { RvRule } from 'app/shared/model/rv-rule.model';

describe('Component Tests', () => {
  describe('RvRule Management Update Component', () => {
    let comp: RvRuleUpdateComponent;
    let fixture: ComponentFixture<RvRuleUpdateComponent>;
    let service: RvRuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvRuleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RvRuleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RvRuleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RvRuleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RvRule(123);
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
        const entity = new RvRule();
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
