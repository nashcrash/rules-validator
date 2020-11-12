import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RulesValidatorTestModule } from '../../../test.module';
import { RvRuleDetailComponent } from 'app/entities/rv-rule/rv-rule-detail.component';
import { RvRule } from 'app/shared/model/rv-rule.model';

describe('Component Tests', () => {
  describe('RvRule Management Detail Component', () => {
    let comp: RvRuleDetailComponent;
    let fixture: ComponentFixture<RvRuleDetailComponent>;
    const route = ({ data: of({ rvRule: new RvRule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RulesValidatorTestModule],
        declarations: [RvRuleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RvRuleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RvRuleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rvRule on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rvRule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
