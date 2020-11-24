import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRock } from 'app/shared/model/rock.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RockService } from './rock.service';
import { RockDeleteDialogComponent } from './rock-delete-dialog.component';

@Component({
  selector: 'jhi-rock',
  templateUrl: './rock.component.html',
})
export class RockComponent implements OnInit, OnDestroy {
  rocks: IRock[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected rockService: RockService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.rocks = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.rockService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IRock[]>) => this.paginateRocks(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.rocks = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRocks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('rockListModification', () => this.reset());
  }

  delete(rock: IRock): void {
    const modalRef = this.modalService.open(RockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rock = rock;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRocks(data: IRock[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.rocks.push(data[i]);
      }
    }
  }
}
