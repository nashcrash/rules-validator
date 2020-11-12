import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRvConverter, RvConverter } from 'app/shared/model/rv-converter.model';
import { RvConverterService } from './rv-converter.service';

@Component({
  selector: 'jhi-rv-converter-update',
  templateUrl: './rv-converter-update.component.html',
})
export class RvConverterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    converterCode: [null, [Validators.required]],
    description: [],
    beanName: [null, [Validators.required]],
  });

  constructor(protected rvConverterService: RvConverterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvConverter }) => {
      this.updateForm(rvConverter);
    });
  }

  updateForm(rvConverter: IRvConverter): void {
    this.editForm.patchValue({
      id: rvConverter.id,
      converterCode: rvConverter.converterCode,
      description: rvConverter.description,
      beanName: rvConverter.beanName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rvConverter = this.createFromForm();
    if (rvConverter.id !== undefined) {
      this.subscribeToSaveResponse(this.rvConverterService.update(rvConverter));
    } else {
      this.subscribeToSaveResponse(this.rvConverterService.create(rvConverter));
    }
  }

  private createFromForm(): IRvConverter {
    return {
      ...new RvConverter(),
      id: this.editForm.get(['id'])!.value,
      converterCode: this.editForm.get(['converterCode'])!.value,
      description: this.editForm.get(['description'])!.value,
      beanName: this.editForm.get(['beanName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRvConverter>>): void {
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
