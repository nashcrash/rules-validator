import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRvOperator } from 'app/shared/model/rv-operator.model';
import { RvOperatorService } from './rv-operator.service';

@Component({
  templateUrl: './rv-operator-delete-dialog.component.html',
})
export class RvOperatorDeleteDialogComponent {
  rvOperator?: IRvOperator;

  constructor(
    protected rvOperatorService: RvOperatorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rvOperatorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rvOperatorListModification');
      this.activeModal.close();
    });
  }
}
