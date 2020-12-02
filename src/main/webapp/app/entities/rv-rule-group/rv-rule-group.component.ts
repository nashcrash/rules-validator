import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRvRuleGroup } from 'app/shared/model/rv-rule-group.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RvRuleGroupService } from './rv-rule-group.service';
import { RvRuleGroupDeleteDialogComponent } from './rv-rule-group-delete-dialog.component';

@Component({
  selector: 'jhi-rv-rule-group',
  templateUrl: './rv-rule-group.component.html',
})
export class RvRuleGroupComponent implements OnInit, OnDestroy {
  rvRuleGroups: IRvRuleGroup[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected rvRuleGroupService: RvRuleGroupService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.rvRuleGroups = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.rvRuleGroupService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IRvRuleGroup[]>) => this.paginateRvRuleGroups(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.rvRuleGroups = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRvRuleGroups();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRvRuleGroup): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRvRuleGroups(): void {
    this.eventSubscriber = this.eventManager.subscribe('rvRuleGroupListModification', () => this.reset());
  }

  delete(rvRuleGroup: IRvRuleGroup): void {
    const modalRef = this.modalService.open(RvRuleGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rvRuleGroup = rvRuleGroup;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRvRuleGroups(data: IRvRuleGroup[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.rvRuleGroups.push(data[i]);
      }
    }
  }
}
