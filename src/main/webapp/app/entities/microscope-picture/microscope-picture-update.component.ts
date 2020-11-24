import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMicroscopePicture, MicroscopePicture } from 'app/shared/model/microscope-picture.model';
import { MicroscopePictureService } from './microscope-picture.service';
import { IMicroscope } from 'app/shared/model/microscope.model';
import { MicroscopeService } from 'app/entities/microscope/microscope.service';

@Component({
  selector: 'jhi-microscope-picture-update',
  templateUrl: './microscope-picture-update.component.html',
})
export class MicroscopePictureUpdateComponent implements OnInit {
  isSaving = false;
  microscopes: IMicroscope[] = [];

  editForm = this.fb.group({
    id: [],
    path: [],
    name: [],
    desc: [],
    order: [],
    microscope: [],
  });

  constructor(
    protected microscopePictureService: MicroscopePictureService,
    protected microscopeService: MicroscopeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ microscopePicture }) => {
      this.updateForm(microscopePicture);

      this.microscopeService.query().subscribe((res: HttpResponse<IMicroscope[]>) => (this.microscopes = res.body || []));
    });
  }

  updateForm(microscopePicture: IMicroscopePicture): void {
    this.editForm.patchValue({
      id: microscopePicture.id,
      path: microscopePicture.path,
      name: microscopePicture.name,
      desc: microscopePicture.desc,
      order: microscopePicture.order,
      microscope: microscopePicture.microscope,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const microscopePicture = this.createFromForm();
    if (microscopePicture.id !== undefined) {
      this.subscribeToSaveResponse(this.microscopePictureService.update(microscopePicture));
    } else {
      this.subscribeToSaveResponse(this.microscopePictureService.create(microscopePicture));
    }
  }

  private createFromForm(): IMicroscopePicture {
    return {
      ...new MicroscopePicture(),
      id: this.editForm.get(['id'])!.value,
      path: this.editForm.get(['path'])!.value,
      name: this.editForm.get(['name'])!.value,
      desc: this.editForm.get(['desc'])!.value,
      order: this.editForm.get(['order'])!.value,
      microscope: this.editForm.get(['microscope'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMicroscopePicture>>): void {
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

  trackById(index: number, item: IMicroscope): any {
    return item.id;
  }
}
