import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRvRuleGroup } from 'app/shared/model/rv-rule-group.model';
import { RvRuleGroupService } from './rv-rule-group.service';

@Component({
  templateUrl: './rv-rule-group-delete-dialog.component.html',
})
export class RvRuleGroupDeleteDialogComponent {
  rvRuleGroup?: IRvRuleGroup;

  constructor(
    protected rvRuleGroupService: RvRuleGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rvRuleGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rvRuleGroupListModification');
      this.activeModal.close();
    });
  }
}
