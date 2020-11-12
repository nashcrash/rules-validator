import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConverter, Converter } from 'app/shared/model/converter.model';
import { ConverterService } from './converter.service';

@Component({
  selector: 'jhi-converter-update',
  templateUrl: './converter-update.component.html',
})
export class ConverterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    converterCode: [null, [Validators.required]],
    description: [],
    beanName: [null, [Validators.required]],
  });

  constructor(protected converterService: ConverterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ converter }) => {
      this.updateForm(converter);
    });
  }

  updateForm(converter: IConverter): void {
    this.editForm.patchValue({
      id: converter.id,
      converterCode: converter.converterCode,
      description: converter.description,
      beanName: converter.beanName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const converter = this.createFromForm();
    if (converter.id !== undefined) {
      this.subscribeToSaveResponse(this.converterService.update(converter));
    } else {
      this.subscribeToSaveResponse(this.converterService.create(converter));
    }
  }

  private createFromForm(): IConverter {
    return {
      ...new Converter(),
      id: this.editForm.get(['id'])!.value,
      converterCode: this.editForm.get(['converterCode'])!.value,
      description: this.editForm.get(['description'])!.value,
      beanName: this.editForm.get(['beanName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConverter>>): void {
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
