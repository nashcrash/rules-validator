import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IParameter, Parameter } from 'app/shared/model/parameter.model';
import { ParameterService } from './parameter.service';
import { IConverter } from 'app/shared/model/converter.model';
import { ConverterService } from 'app/entities/converter/converter.service';

@Component({
  selector: 'jhi-parameter-update',
  templateUrl: './parameter-update.component.html',
})
export class ParameterUpdateComponent implements OnInit {
  isSaving = false;
  converters: IConverter[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    converters: [],
  });

  constructor(
    protected parameterService: ParameterService,
    protected converterService: ConverterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parameter }) => {
      this.updateForm(parameter);

      this.converterService.query().subscribe((res: HttpResponse<IConverter[]>) => (this.converters = res.body || []));
    });
  }

  updateForm(parameter: IParameter): void {
    this.editForm.patchValue({
      id: parameter.id,
      value: parameter.value,
      converters: parameter.converters,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parameter = this.createFromForm();
    if (parameter.id !== undefined) {
      this.subscribeToSaveResponse(this.parameterService.update(parameter));
    } else {
      this.subscribeToSaveResponse(this.parameterService.create(parameter));
    }
  }

  private createFromForm(): IParameter {
    return {
      ...new Parameter(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      converters: this.editForm.get(['converters'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParameter>>): void {
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
