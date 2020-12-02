import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvRuleGroupDetailComponent } from 'app/entities/rv-rule-group/rv-rule-group-detail.component';
import { RvRuleGroup } from 'app/shared/model/rv-rule-group.model';

describe('Component Tests', () => {
  describe('RvRuleGroup Management Detail Component', () => {
    let comp: RvRuleGroupDetailComponent;
    let fixture: ComponentFixture<RvRuleGroupDetailComponent>;
    const route = ({ data: of({ rvRuleGroup: new RvRuleGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvRuleGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RvRuleGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RvRuleGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rvRuleGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rvRuleGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
