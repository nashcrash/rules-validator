import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConverter } from 'app/shared/model/converter.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ConverterService } from './converter.service';
import { ConverterDeleteDialogComponent } from './converter-delete-dialog.component';

@Component({
  selector: 'jhi-converter',
  templateUrl: './converter.component.html',
})
export class ConverterComponent implements OnInit, OnDestroy {
  converters: IConverter[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected converterService: ConverterService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.converters = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.converterService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IConverter[]>) => this.paginateConverters(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.converters = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConverters();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConverter): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConverters(): void {
    this.eventSubscriber = this.eventManager.subscribe('converterListModification', () => this.reset());
  }

  delete(converter: IConverter): void {
    const modalRef = this.modalService.open(ConverterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.converter = converter;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateConverters(data: IConverter[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.converters.push(data[i]);
      }
    }
  }
}
