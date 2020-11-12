import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOperator, Operator } from 'app/shared/model/operator.model';
import { OperatorService } from './operator.service';

@Component({
  selector: 'jhi-operator-update',
  templateUrl: './operator-update.component.html',
})
export class OperatorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    operatorCode: [null, [Validators.required]],
    description: [],
    beanName: [null, [Validators.required]],
    numberOfParameters: [],
  });

  constructor(protected operatorService: OperatorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operator }) => {
      this.updateForm(operator);
    });
  }

  updateForm(operator: IOperator): void {
    this.editForm.patchValue({
      id: operator.id,
      operatorCode: operator.operatorCode,
      description: operator.description,
      beanName: operator.beanName,
      numberOfParameters: operator.numberOfParameters,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operator = this.createFromForm();
    if (operator.id !== undefined) {
      this.subscribeToSaveResponse(this.operatorService.update(operator));
    } else {
      this.subscribeToSaveResponse(this.operatorService.create(operator));
    }
  }

  private createFromForm(): IOperator {
    return {
      ...new Operator(),
      id: this.editForm.get(['id'])!.value,
      operatorCode: this.editForm.get(['operatorCode'])!.value,
      description: this.editForm.get(['description'])!.value,
      beanName: this.editForm.get(['beanName'])!.value,
      numberOfParameters: this.editForm.get(['numberOfParameters'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperator>>): void {
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
}
