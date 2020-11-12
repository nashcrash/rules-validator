import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParameter } from 'app/shared/model/parameter.model';
import { ParameterService } from './parameter.service';

@Component({
  templateUrl: './parameter-delete-dialog.component.html',
})
export class ParameterDeleteDialogComponent {
  parameter?: IParameter;

  constructor(protected parameterService: ParameterService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.parameterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('parameterListModification');
      this.activeModal.close();
    });
  }
}
