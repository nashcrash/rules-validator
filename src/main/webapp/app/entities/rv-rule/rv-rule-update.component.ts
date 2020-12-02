import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRvRule, RvRule } from 'app/shared/model/rv-rule.model';
import { RvRuleService } from './rv-rule.service';
import { IRvRuleGroup } from 'app/shared/model/rv-rule-group.model';
import { RvRuleGroupService } from 'app/entities/rv-rule-group/rv-rule-group.service';
import { IRvOperator } from 'app/shared/model/rv-operator.model';
import { RvOperatorService } from 'app/entities/rv-operator/rv-operator.service';
import { IRvParam } from 'app/shared/model/rv-param.model';
import { RvParamService } from 'app/entities/rv-param/rv-param.service';

type SelectableEntity = IRvRuleGroup | IRvOperator | IRvParam;

@Component({
  selector: 'jhi-rv-rule-update',
  templateUrl: './rv-rule-update.component.html',
})
export class RvRuleUpdateComponent implements OnInit {
  isSaving = false;
  rvrulegroups: IRvRuleGroup[] = [];
  rvoperators: IRvOperator[] = [];
  rvparams: IRvParam[] = [];

  editForm = this.fb.group({
    id: [],
    ruleCode: [null, [Validators.required]],
    description: [],
    level: [null, [Validators.required]],
    mode: [null, [Validators.required]],
    groupId: [],
    operatorId: [],
    rvParams: [],
  });

  constructor(
    protected rvRuleService: RvRuleService,
    protected rvRuleGroupService: RvRuleGroupService,
    protected rvOperatorService: RvOperatorService,
    protected rvParamService: RvParamService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvRule }) => {
      this.updateForm(rvRule);

      this.rvRuleGroupService.query().subscribe((res: HttpResponse<IRvRuleGroup[]>) => (this.rvrulegroups = res.body || []));

      this.rvOperatorService.query().subscribe((res: HttpResponse<IRvOperator[]>) => (this.rvoperators = res.body || []));

      this.rvParamService.query().subscribe((res: HttpResponse<IRvParam[]>) => (this.rvparams = res.body || []));
    });
  }

  updateForm(rvRule: IRvRule): void {
    this.editForm.patchValue({
      id: rvRule.id,
      ruleCode: rvRule.ruleCode,
      description: rvRule.description,
      level: rvRule.level,
      mode: rvRule.mode,
      groupId: rvRule.groupId,
      operatorId: rvRule.operator ? rvRule.operator.id : undefined,
      rvParams: rvRule.rvParams,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rvRule = this.createFromForm();
    if (rvRule.id !== undefined) {
      this.subscribeToSaveResponse(this.rvRuleService.update(rvRule));
    } else {
      this.subscribeToSaveResponse(this.rvRuleService.create(rvRule));
    }
  }

  private createFromForm(): IRvRule {
    return {
      ...new RvRule(),
      id: this.editForm.get(['id'])!.value,
      ruleCode: this.editForm.get(['ruleCode'])!.value,
      description: this.editForm.get(['description'])!.value,
      level: this.editForm.get(['level'])!.value,
      mode: this.editForm.get(['mode'])!.value,
      groupId: this.editForm.get(['groupId'])!.value,
      operator: new RvOperator(this.editForm.get(['operatorId'])!.value),
      rvParams: this.editForm.get(['rvParams'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRvRule>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IRvParam[], option: IRvParam): IRvParam {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
