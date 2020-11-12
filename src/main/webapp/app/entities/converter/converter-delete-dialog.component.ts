import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConverter } from 'app/shared/model/converter.model';
import { ConverterService } from './converter.service';

@Component({
  templateUrl: './converter-delete-dialog.component.html',
})
export class ConverterDeleteDialogComponent {
  converter?: IConverter;

  constructor(protected converterService: ConverterService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.converterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('converterListModification');
      this.activeModal.close();
    });
  }
}
