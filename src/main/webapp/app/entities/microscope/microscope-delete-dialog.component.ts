import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMicroscope } from 'app/shared/model/microscope.model';
import { MicroscopeService } from './microscope.service';

@Component({
  templateUrl: './microscope-delete-dialog.component.html',
})
export class MicroscopeDeleteDialogComponent {
  microscope?: IMicroscope;

  constructor(
    protected microscopeService: MicroscopeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.microscopeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('microscopeListModification');
      this.activeModal.close();
    });
  }
}
