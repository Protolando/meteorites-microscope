import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRock } from 'app/shared/model/rock.model';
import { RockService } from './rock.service';

@Component({
  templateUrl: './rock-delete-dialog.component.html',
})
export class RockDeleteDialogComponent {
  rock?: IRock;

  constructor(protected rockService: RockService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rockService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rockListModification');
      this.activeModal.close();
    });
  }
}
