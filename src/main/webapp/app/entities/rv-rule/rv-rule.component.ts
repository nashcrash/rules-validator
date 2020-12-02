import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRvRule } from 'app/shared/model/rv-rule.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RvRuleService } from './rv-rule.service';
import { RvRuleDeleteDialogComponent } from './rv-rule-delete-dialog.component';

@Component({
  selector: 'jhi-rv-rule',
  templateUrl: './rv-rule.component.html',
})
export class RvRuleComponent implements OnInit, OnDestroy {
  rvRules: IRvRule[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected rvRuleService: RvRuleService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.rvRules = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.rvRuleService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IRvRule[]>) => this.paginateRvRules(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.rvRules = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRvRules();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRvRule): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRvRules(): void {
    this.eventSubscriber = this.eventManager.subscribe('rvRuleListModification', () => this.reset());
  }

  delete(rvRule: IRvRule): void {
    const modalRef = this.modalService.open(RvRuleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rvRule = rvRule;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRvRules(data: IRvRule[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.rvRules.push(data[i]);
      }
    }
  }
}
