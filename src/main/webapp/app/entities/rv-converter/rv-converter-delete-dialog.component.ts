import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRvConverter } from 'app/shared/model/rv-converter.model';
import { RvConverterService } from './rv-converter.service';

@Component({
  templateUrl: './rv-converter-delete-dialog.component.html',
})
export class RvConverterDeleteDialogComponent {
  rvConverter?: IRvConverter;

  constructor(
    protected rvConverterService: RvConverterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rvConverterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rvConverterListModification');
      this.activeModal.close();
    });
  }
}
