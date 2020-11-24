import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRock, Rock } from 'app/shared/model/rock.model';
import { RockService } from './rock.service';

@Component({
  selector: 'jhi-rock-update',
  templateUrl: './rock-update.component.html',
})
export class RockUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    desc: [],
    picturePath: [],
  });

  constructor(protected rockService: RockService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rock }) => {
      this.updateForm(rock);
    });
  }

  updateForm(rock: IRock): void {
    this.editForm.patchValue({
      id: rock.id,
      name: rock.name,
      desc: rock.desc,
      picturePath: rock.picturePath,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rock = this.createFromForm();
    if (rock.id !== undefined) {
      this.subscribeToSaveResponse(this.rockService.update(rock));
    } else {
      this.subscribeToSaveResponse(this.rockService.create(rock));
    }
  }

  private createFromForm(): IRock {
    return {
      ...new Rock(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      desc: this.editForm.get(['desc'])!.value,
      picturePath: this.editForm.get(['picturePath'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRock>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
