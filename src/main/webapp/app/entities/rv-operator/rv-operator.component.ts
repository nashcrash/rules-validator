import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRvOperator } from 'app/shared/model/rv-operator.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RvOperatorService } from './rv-operator.service';
import { RvOperatorDeleteDialogComponent } from './rv-operator-delete-dialog.component';

@Component({
  selector: 'jhi-rv-operator',
  templateUrl: './rv-operator.component.html',
})
export class RvOperatorComponent implements OnInit, OnDestroy {
  rvOperators: IRvOperator[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected rvOperatorService: RvOperatorService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.rvOperators = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.rvOperatorService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IRvOperator[]>) => this.paginateRvOperators(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.rvOperators = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRvOperators();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRvOperator): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRvOperators(): void {
    this.eventSubscriber = this.eventManager.subscribe('rvOperatorListModification', () => this.reset());
  }

  delete(rvOperator: IRvOperator): void {
    const modalRef = this.modalService.open(RvOperatorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rvOperator = rvOperator;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRvOperators(data: IRvOperator[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.rvOperators.push(data[i]);
      }
    }
  }
}
