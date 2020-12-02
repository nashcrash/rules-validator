import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRvOperatorParam } from 'app/shared/model/rv-operator-param.model';
import { RvOperatorParamService } from './rv-operator-param.service';

@Component({
  templateUrl: './rv-operator-param-delete-dialog.component.html',
})
export class RvOperatorParamDeleteDialogComponent {
  rvOperatorParam?: IRvOperatorParam;

  constructor(
    protected rvOperatorParamService: RvOperatorParamService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rvOperatorParamService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rvOperatorParamListModification');
      this.activeModal.close();
    });
  }
}
