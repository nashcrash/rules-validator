import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRvRuleGroup, RvRuleGroup } from 'app/shared/model/rv-rule-group.model';
import { RvRuleGroupService } from './rv-rule-group.service';

@Component({
  selector: 'jhi-rv-rule-group-update',
  templateUrl: './rv-rule-group-update.component.html',
})
export class RvRuleGroupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    ruleGroupName: [null, [Validators.required]],
  });

  constructor(protected rvRuleGroupService: RvRuleGroupService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvRuleGroup }) => {
      this.updateForm(rvRuleGroup);
    });
  }

  updateForm(rvRuleGroup: IRvRuleGroup): void {
    this.editForm.patchValue({
      id: rvRuleGroup.id,
      ruleGroupName: rvRuleGroup.ruleGroupName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rvRuleGroup = this.createFromForm();
    if (rvRuleGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.rvRuleGroupService.update(rvRuleGroup));
    } else {
      this.subscribeToSaveResponse(this.rvRuleGroupService.create(rvRuleGroup));
    }
  }

  private createFromForm(): IRvRuleGroup {
    return {
      ...new RvRuleGroup(),
      id: this.editForm.get(['id'])!.value,
      ruleGroupName: this.editForm.get(['ruleGroupName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRvRuleGroup>>): void {
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
