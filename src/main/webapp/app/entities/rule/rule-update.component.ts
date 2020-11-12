import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRule, Rule } from 'app/shared/model/rule.model';
import { RuleService } from './rule.service';
import { IOperator } from 'app/shared/model/operator.model';
import { OperatorService } from 'app/entities/operator/operator.service';
import { IParameter } from 'app/shared/model/parameter.model';
import { ParameterService } from 'app/entities/parameter/parameter.service';

type SelectableEntity = IOperator | IParameter;

@Component({
  selector: 'jhi-rule-update',
  templateUrl: './rule-update.component.html',
})
export class RuleUpdateComponent implements OnInit {
  isSaving = false;
  operators: IOperator[] = [];
  parameters: IParameter[] = [];

  editForm = this.fb.group({
    id: [],
    ruleCode: [null, [Validators.required]],
    description: [],
    mode: [null, [Validators.required]],
    operatorId: [],
    parameters: [],
  });

  constructor(
    protected ruleService: RuleService,
    protected operatorService: OperatorService,
    protected parameterService: ParameterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rule }) => {
      this.updateForm(rule);

      this.operatorService.query().subscribe((res: HttpResponse<IOperator[]>) => (this.operators = res.body || []));

      this.parameterService.query().subscribe((res: HttpResponse<IParameter[]>) => (this.parameters = res.body || []));
    });
  }

  updateForm(rule: IRule): void {
    this.editForm.patchValue({
      id: rule.id,
      ruleCode: rule.ruleCode,
      description: rule.description,
      mode: rule.mode,
      operatorId: rule.operatorId,
      parameters: rule.parameters,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rule = this.createFromForm();
    if (rule.id !== undefined) {
      this.subscribeToSaveResponse(this.ruleService.update(rule));
    } else {
      this.subscribeToSaveResponse(this.ruleService.create(rule));
    }
  }

  private createFromForm(): IRule {
    return {
      ...new Rule(),
      id: this.editForm.get(['id'])!.value,
      ruleCode: this.editForm.get(['ruleCode'])!.value,
      description: this.editForm.get(['description'])!.value,
      mode: this.editForm.get(['mode'])!.value,
      operatorId: this.editForm.get(['operatorId'])!.value,
      parameters: this.editForm.get(['parameters'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRule>>): void {
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

  getSelected(selectedVals: IParameter[], option: IParameter): IParameter {
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
