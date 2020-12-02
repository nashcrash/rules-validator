import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRvRule } from 'app/shared/model/rv-rule.model';
import { RvRuleService } from './rv-rule.service';

@Component({
  templateUrl: './rv-rule-delete-dialog.component.html',
})
export class RvRuleDeleteDialogComponent {
  rvRule?: IRvRule;

  constructor(protected rvRuleService: RvRuleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rvRuleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rvRuleListModification');
      this.activeModal.close();
    });
  }
}
