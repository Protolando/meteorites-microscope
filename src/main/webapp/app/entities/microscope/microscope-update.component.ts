import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMicroscope, Microscope } from 'app/shared/model/microscope.model';
import { MicroscopeService } from './microscope.service';
import { IRock } from 'app/shared/model/rock.model';
import { RockService } from 'app/entities/rock/rock.service';

@Component({
  selector: 'jhi-microscope-update',
  templateUrl: './microscope-update.component.html',
})
export class MicroscopeUpdateComponent implements OnInit {
  isSaving = false;
  rocks: IRock[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    rock: [],
  });

  constructor(
    protected microscopeService: MicroscopeService,
    protected rockService: RockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ microscope }) => {
      this.updateForm(microscope);

      this.rockService.query().subscribe((res: HttpResponse<IRock[]>) => (this.rocks = res.body || []));
    });
  }

  updateForm(microscope: IMicroscope): void {
    this.editForm.patchValue({
      id: microscope.id,
      name: microscope.name,
      rock: microscope.rock,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const microscope = this.createFromForm();
    if (microscope.id !== undefined) {
      this.subscribeToSaveResponse(this.microscopeService.update(microscope));
    } else {
      this.subscribeToSaveResponse(this.microscopeService.create(microscope));
    }
  }

  private createFromForm(): IMicroscope {
    return {
      ...new Microscope(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      rock: this.editForm.get(['rock'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMicroscope>>): void {
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

  trackById(index: number, item: IRock): any {
    return item.id;
  }
}
