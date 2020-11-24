import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMicroscopePicture } from 'app/shared/model/microscope-picture.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MicroscopePictureService } from './microscope-picture.service';
import { MicroscopePictureDeleteDialogComponent } from './microscope-picture-delete-dialog.component';

@Component({
  selector: 'jhi-microscope-picture',
  templateUrl: './microscope-picture.component.html',
})
export class MicroscopePictureComponent implements OnInit, OnDestroy {
  microscopePictures: IMicroscopePicture[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected microscopePictureService: MicroscopePictureService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.microscopePictures = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.microscopePictureService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IMicroscopePicture[]>) => this.paginateMicroscopePictures(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.microscopePictures = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMicroscopePictures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMicroscopePicture): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMicroscopePictures(): void {
    this.eventSubscriber = this.eventManager.subscribe('microscopePictureListModification', () => this.reset());
  }

  delete(microscopePicture: IMicroscopePicture): void {
    const modalRef = this.modalService.open(MicroscopePictureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.microscopePicture = microscopePicture;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMicroscopePictures(data: IMicroscopePicture[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.microscopePictures.push(data[i]);
      }
    }
  }
}
