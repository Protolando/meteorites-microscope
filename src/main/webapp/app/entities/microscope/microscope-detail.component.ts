import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMicroscope } from 'app/shared/model/microscope.model';

@Component({
  selector: 'jhi-microscope-detail',
  templateUrl: './microscope-detail.component.html',
})
export class MicroscopeDetailComponent implements OnInit {
  microscope: IMicroscope | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ microscope }) => (this.microscope = microscope));
  }

  previousState(): void {
    window.history.back();
  }
}
