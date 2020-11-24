import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRock } from 'app/shared/model/rock.model';

@Component({
  selector: 'jhi-rock-detail',
  templateUrl: './rock-detail.component.html',
})
export class RockDetailComponent implements OnInit {
  rock: IRock | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rock }) => (this.rock = rock));
  }

  previousState(): void {
    window.history.back();
  }
}
