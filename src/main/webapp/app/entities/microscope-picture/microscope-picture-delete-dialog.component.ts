import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMicroscopePicture } from 'app/shared/model/microscope-picture.model';
import { MicroscopePictureService } from './microscope-picture.service';

@Component({
  templateUrl: './microscope-picture-delete-dialog.component.html',
})
export class MicroscopePictureDeleteDialogComponent {
  microscopePicture?: IMicroscopePicture;

  constructor(
    protected microscopePictureService: MicroscopePictureService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.microscopePictureService.delete(id).subscribe(() => {
      this.eventManager.broadcast('microscopePictureListModification');
      this.activeModal.close();
    });
  }
}
