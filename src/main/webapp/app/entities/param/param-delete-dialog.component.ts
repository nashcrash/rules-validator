import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParam } from 'app/shared/model/param.model';
import { ParamService } from './param.service';

@Component({
  templateUrl: './param-delete-dialog.component.html',
})
export class ParamDeleteDialogComponent {
  param?: IParam;

  constructor(protected paramService: ParamService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paramService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paramListModification');
      this.activeModal.close();
    });
  }
}
