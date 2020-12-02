import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRvOperatorParam } from 'app/shared/model/rv-operator-param.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RvOperatorParamService } from './rv-operator-param.service';
import { RvOperatorParamDeleteDialogComponent } from './rv-operator-param-delete-dialog.component';

@Component({
  selector: 'jhi-rv-operator-param',
  templateUrl: './rv-operator-param.component.html',
})
export class RvOperatorParamComponent implements OnInit, OnDestroy {
  rvOperatorParams: IRvOperatorParam[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected rvOperatorParamService: RvOperatorParamService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.rvOperatorParams = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.rvOperatorParamService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IRvOperatorParam[]>) => this.paginateRvOperatorParams(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.rvOperatorParams = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRvOperatorParams();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRvOperatorParam): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRvOperatorParams(): void {
    this.eventSubscriber = this.eventManager.subscribe('rvOperatorParamListModification', () => this.reset());
  }

  delete(rvOperatorParam: IRvOperatorParam): void {
    const modalRef = this.modalService.open(RvOperatorParamDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rvOperatorParam = rvOperatorParam;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRvOperatorParams(data: IRvOperatorParam[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.rvOperatorParams.push(data[i]);
      }
    }
  }
}
