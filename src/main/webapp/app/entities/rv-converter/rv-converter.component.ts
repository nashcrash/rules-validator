import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRvConverter } from 'app/shared/model/rv-converter.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RvConverterService } from './rv-converter.service';
import { RvConverterDeleteDialogComponent } from './rv-converter-delete-dialog.component';

@Component({
  selector: 'jhi-rv-converter',
  templateUrl: './rv-converter.component.html',
})
export class RvConverterComponent implements OnInit, OnDestroy {
  rvConverters: IRvConverter[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected rvConverterService: RvConverterService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.rvConverters = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.rvConverterService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IRvConverter[]>) => this.paginateRvConverters(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.rvConverters = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRvConverters();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRvConverter): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRvConverters(): void {
    this.eventSubscriber = this.eventManager.subscribe('rvConverterListModification', () => this.reset());
  }

  delete(rvConverter: IRvConverter): void {
    const modalRef = this.modalService.open(RvConverterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rvConverter = rvConverter;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRvConverters(data: IRvConverter[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.rvConverters.push(data[i]);
      }
    }
  }
}
