import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParam } from 'app/shared/model/param.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ParamService } from './param.service';
import { ParamDeleteDialogComponent } from './param-delete-dialog.component';

@Component({
  selector: 'jhi-param',
  templateUrl: './param.component.html',
})
export class ParamComponent implements OnInit, OnDestroy {
  params: IParam[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected paramService: ParamService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.params = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.paramService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IParam[]>) => this.paginateParams(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.params = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInParams();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IParam): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInParams(): void {
    this.eventSubscriber = this.eventManager.subscribe('paramListModification', () => this.reset());
  }

  delete(param: IParam): void {
    const modalRef = this.modalService.open(ParamDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.param = param;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateParams(data: IParam[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.params.push(data[i]);
      }
    }
  }
}
