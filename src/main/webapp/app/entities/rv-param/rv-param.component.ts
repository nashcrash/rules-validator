import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRvParam } from 'app/shared/model/rv-param.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RvParamService } from './rv-param.service';
import { RvParamDeleteDialogComponent } from './rv-param-delete-dialog.component';

@Component({
  selector: 'jhi-rv-param',
  templateUrl: './rv-param.component.html',
})
export class RvParamComponent implements OnInit, OnDestroy {
  rvParams: IRvParam[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected rvParamService: RvParamService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.rvParams = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.rvParamService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IRvParam[]>) => this.paginateRvParams(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.rvParams = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRvParams();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRvParam): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRvParams(): void {
    this.eventSubscriber = this.eventManager.subscribe('rvParamListModification', () => this.reset());
  }

  delete(rvParam: IRvParam): void {
    const modalRef = this.modalService.open(RvParamDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rvParam = rvParam;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRvParams(data: IRvParam[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.rvParams.push(data[i]);
      }
    }
  }
}
