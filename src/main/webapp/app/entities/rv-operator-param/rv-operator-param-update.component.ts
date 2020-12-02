import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRvOperatorParam, RvOperatorParam } from 'app/shared/model/rv-operator-param.model';
import { RvOperatorParamService } from './rv-operator-param.service';
import { IRvOperator } from 'app/shared/model/rv-operator.model';
import { RvOperatorService } from 'app/entities/rv-operator/rv-operator.service';

@Component({
  selector: 'jhi-rv-operator-param-update',
  templateUrl: './rv-operator-param-update.component.html',
})
export class RvOperatorParamUpdateComponent implements OnInit {
  isSaving = false;
  rvoperators: IRvOperator[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    type: [null, [Validators.required]],
    operatorId: [],
  });

  constructor(
    protected rvOperatorParamService: RvOperatorParamService,
    protected rvOperatorService: RvOperatorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvOperatorParam }) => {
      this.updateForm(rvOperatorParam);

      this.rvOperatorService.query().subscribe((res: HttpResponse<IRvOperator[]>) => (this.rvoperators = res.body || []));
    });
  }

  updateForm(rvOperatorParam: IRvOperatorParam): void {
    this.editForm.patchValue({
      id: rvOperatorParam.id,
      name: rvOperatorParam.name,
      description: rvOperatorParam.description,
      type: rvOperatorParam.type,
      operatorId: rvOperatorParam.operatorId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rvOperatorParam = this.createFromForm();
    if (rvOperatorParam.id !== undefined) {
      this.subscribeToSaveResponse(this.rvOperatorParamService.update(rvOperatorParam));
    } else {
      this.subscribeToSaveResponse(this.rvOperatorParamService.create(rvOperatorParam));
    }
  }

  private createFromForm(): IRvOperatorParam {
    return {
      ...new RvOperatorParam(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      type: this.editForm.get(['type'])!.value,
      operatorId: this.editForm.get(['operatorId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRvOperatorParam>>): void {
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

  trackById(index: number, item: IRvOperator): any {
    return item.id;
  }
}
