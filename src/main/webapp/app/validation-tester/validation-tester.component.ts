import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { IRvRuleGroup } from 'app/shared/model/rv-rule-group.model';
import { RvRuleGroupService } from 'app/entities/rv-rule-group/rv-rule-group.service';
import { IRvRule } from 'app/shared/model/rv-rule.model';
import { RvRuleService } from 'app/entities/rv-rule/rv-rule.service';
import { ValidationRequest, ValidationResult, ValidationResultDetail } from 'app/shared/model/validation-tester.dto';
import { ValidationTesterService } from './validation-tester.service';

type SelectableEntity = IRvRule | IRvRuleGroup;

@Component({
  selector: 'jhi-validation-tester',
  templateUrl: './validation-tester.component.html',
  styleUrls: ['validation-tester.component.scss'],
})
export class ValidationTesterComponent implements OnInit {
  rvrules: IRvRule[] = [];
  rvrulegroups: IRvRuleGroup[] = [];
  result: ValidationResult | null = null;
  predicate: string;
  ascending: boolean;

  editForm = this.fb.group({
    type: ['JSON', [Validators.required]],
    ruleCode: [null, [Validators.required]],
    group: [],
    model: [null, [Validators.required]],
  });

  constructor(
    protected validationTesterService: ValidationTesterService,
    protected rvRuleGroupService: RvRuleGroupService,
    protected rvRuleService: RvRuleService,
    private fb: FormBuilder
  ) {
    this.predicate = 'ruleCode';
    this.ascending = true;
  }

  ngOnInit(): void {
    this.rvRuleGroupService.query().subscribe((res: HttpResponse<IRvRuleGroup[]>) => (this.rvrulegroups = res.body || []));
  }

  private createFromForm(): ValidationRequest {
    return {
      ...new ValidationRequest(),
      type: this.editForm.get(['type'])!.value,
      ruleCode: this.editForm.get(['ruleCode'])!.value,
      model: this.editForm.get(['model'])!.value,
    };
  }

  validate(): void {
    const request = this.createFromForm();
    this.validationTesterService.validate(request).subscribe((res: HttpResponse<ValidationResult>) => this.onValidationResult(res.body));
  }

  trackByRuleCode(index: number, item: IRvRule): any {
    return item.ruleCode;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  onChangeGroup(): void {
    const group: number = this.editForm.get(['group'])!.value;
    if (group === 0) {
      this.rvRuleService.query().subscribe((res: HttpResponse<IRvRule[]>) => (this.rvrules = res.body || []));
    } else {
      this.rvRuleService.query({ groupId: group }).subscribe((res: HttpResponse<IRvRule[]>) => (this.rvrules = res.body || []));
    }
  }

  protected onValidationResult(validationResult: ValidationResult | null): void {
    this.result = validationResult;
  }
}
