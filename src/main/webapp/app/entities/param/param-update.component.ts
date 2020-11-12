import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IParam, Param } from 'app/shared/model/param.model';
import { ParamService } from './param.service';
import { IConverter } from 'app/shared/model/converter.model';
import { ConverterService } from 'app/entities/converter/converter.service';

@Component({
  selector: 'jhi-param-update',
  templateUrl: './param-update.component.html',
})
export class ParamUpdateComponent implements OnInit {
  isSaving = false;
  converters: IConverter[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    converters: [],
  });

  constructor(
    protected paramService: ParamService,
    protected converterService: ConverterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ param }) => {
      this.updateForm(param);

      this.converterService.query().subscribe((res: HttpResponse<IConverter[]>) => (this.converters = res.body || []));
    });
  }

  updateForm(param: IParam): void {
    this.editForm.patchValue({
      id: param.id,
      value: param.value,
      converters: param.converters,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const param = this.createFromForm();
    if (param.id !== undefined) {
      this.subscribeToSaveResponse(this.paramService.update(param));
    } else {
      this.subscribeToSaveResponse(this.paramService.create(param));
    }
  }

  private createFromForm(): IParam {
    return {
      ...new Param(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      converters: this.editForm.get(['converters'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParam>>): void {
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

  trackById(index: number, item: IConverter): any {
    return item.id;
  }

  getSelected(selectedVals: IConverter[], option: IConverter): IConverter {
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
