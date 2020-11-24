import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMicroscopePicture } from 'app/shared/model/microscope-picture.model';

@Component({
  selector: 'jhi-microscope-picture-detail',
  templateUrl: './microscope-picture-detail.component.html',
})
export class MicroscopePictureDetailComponent implements OnInit {
  microscopePicture: IMicroscopePicture | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ microscopePicture }) => (this.microscopePicture = microscopePicture));
  }

  previousState(): void {
    window.history.back();
  }
}
