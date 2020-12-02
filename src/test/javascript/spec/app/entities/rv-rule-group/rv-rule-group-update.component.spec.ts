import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvRuleGroupUpdateComponent } from 'app/entities/rv-rule-group/rv-rule-group-update.component';
import { RvRuleGroupService } from 'app/entities/rv-rule-group/rv-rule-group.service';
import { RvRuleGroup } from 'app/shared/model/rv-rule-group.model';

describe('Component Tests', () => {
  describe('RvRuleGroup Management Update Component', () => {
    let comp: RvRuleGroupUpdateComponent;
    let fixture: ComponentFixture<RvRuleGroupUpdateComponent>;
    let service: RvRuleGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvRuleGroupUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RvRuleGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RvRuleGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RvRuleGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RvRuleGroup(123);
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
        const entity = new RvRuleGroup();
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
