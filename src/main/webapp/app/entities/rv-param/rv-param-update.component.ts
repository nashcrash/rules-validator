import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRvParam, RvParam } from 'app/shared/model/rv-param.model';
import { RvParamService } from './rv-param.service';
import { IRvConverter } from 'app/shared/model/rv-converter.model';
import { RvConverterService } from 'app/entities/rv-converter/rv-converter.service';

@Component({
  selector: 'jhi-rv-param-update',
  templateUrl: './rv-param-update.component.html',
})
export class RvParamUpdateComponent implements OnInit {
  isSaving = false;
  rvconverters: IRvConverter[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    rvConverters: [],
  });

  constructor(
    protected rvParamService: RvParamService,
    protected rvConverterService: RvConverterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvParam }) => {
      this.updateForm(rvParam);

      this.rvConverterService.query().subscribe((res: HttpResponse<IRvConverter[]>) => (this.rvconverters = res.body || []));
    });
  }

  updateForm(rvParam: IRvParam): void {
    this.editForm.patchValue({
      id: rvParam.id,
      value: rvParam.value,
      rvConverters: rvParam.rvConverters,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rvParam = this.createFromForm();
    if (rvParam.id !== undefined) {
      this.subscribeToSaveResponse(this.rvParamService.update(rvParam));
    } else {
      this.subscribeToSaveResponse(this.rvParamService.create(rvParam));
    }
  }

  private createFromForm(): IRvParam {
    return {
      ...new RvParam(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      rvConverters: this.editForm.get(['rvConverters'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRvParam>>): void {
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

  trackById(index: number, item: IRvConverter): any {
    return item.id;
  }

  getSelected(selectedVals: IRvConverter[], option: IRvConverter): IRvConverter {
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
