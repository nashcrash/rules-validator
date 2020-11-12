import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRvParam } from 'app/shared/model/rv-param.model';
import { RvParamService } from './rv-param.service';

@Component({
  templateUrl: './rv-param-delete-dialog.component.html',
})
export class RvParamDeleteDialogComponent {
  rvParam?: IRvParam;

  constructor(protected rvParamService: RvParamService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rvParamService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rvParamListModification');
      this.activeModal.close();
    });
  }
}
