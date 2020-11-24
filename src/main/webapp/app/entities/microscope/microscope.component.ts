import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMicroscope } from 'app/shared/model/microscope.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MicroscopeService } from './microscope.service';
import { MicroscopeDeleteDialogComponent } from './microscope-delete-dialog.component';

@Component({
  selector: 'jhi-microscope',
  templateUrl: './microscope.component.html',
})
export class MicroscopeComponent implements OnInit, OnDestroy {
  microscopes: IMicroscope[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected microscopeService: MicroscopeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.microscopes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.microscopeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IMicroscope[]>) => this.paginateMicroscopes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.microscopes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMicroscopes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMicroscope): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMicroscopes(): void {
    this.eventSubscriber = this.eventManager.subscribe('microscopeListModification', () => this.reset());
  }

  delete(microscope: IMicroscope): void {
    const modalRef = this.modalService.open(MicroscopeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.microscope = microscope;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMicroscopes(data: IMicroscope[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.microscopes.push(data[i]);
      }
    }
  }
}
