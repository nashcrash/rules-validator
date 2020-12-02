import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRvOperator, RvOperator } from 'app/shared/model/rv-operator.model';
import { RvOperatorService } from './rv-operator.service';

@Component({
  selector: 'jhi-rv-operator-update',
  templateUrl: './rv-operator-update.component.html',
})
export class RvOperatorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    operatorCode: [null, [Validators.required]],
    description: [],
    beanName: [null, [Validators.required]],
    numberOfParams: [],
  });

  constructor(protected rvOperatorService: RvOperatorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvOperator }) => {
      this.updateForm(rvOperator);
    });
  }

  updateForm(rvOperator: IRvOperator): void {
    this.editForm.patchValue({
      id: rvOperator.id,
      operatorCode: rvOperator.operatorCode,
      description: rvOperator.description,
      beanName: rvOperator.beanName,
      numberOfParams: rvOperator.numberOfParams,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rvOperator = this.createFromForm();
    if (rvOperator.id !== undefined) {
      this.subscribeToSaveResponse(this.rvOperatorService.update(rvOperator));
    } else {
      this.subscribeToSaveResponse(this.rvOperatorService.create(rvOperator));
    }
  }

  private createFromForm(): IRvOperator {
    return {
      ...new RvOperator(),
      id: this.editForm.get(['id'])!.value,
      operatorCode: this.editForm.get(['operatorCode'])!.value,
      description: this.editForm.get(['description'])!.value,
      beanName: this.editForm.get(['beanName'])!.value,
      numberOfParams: this.editForm.get(['numberOfParams'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRvOperator>>): void {
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
