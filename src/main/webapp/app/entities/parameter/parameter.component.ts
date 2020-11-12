import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParameter } from 'app/shared/model/parameter.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ParameterService } from './parameter.service';
import { ParameterDeleteDialogComponent } from './parameter-delete-dialog.component';

@Component({
  selector: 'jhi-parameter',
  templateUrl: './parameter.component.html',
})
export class ParameterComponent implements OnInit, OnDestroy {
  parameters: IParameter[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected parameterService: ParameterService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.parameters = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.parameterService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IParameter[]>) => this.paginateParameters(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.parameters = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInParameters();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IParameter): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInParameters(): void {
    this.eventSubscriber = this.eventManager.subscribe('parameterListModification', () => this.reset());
  }

  delete(parameter: IParameter): void {
    const modalRef = this.modalService.open(ParameterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.parameter = parameter;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateParameters(data: IParameter[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.parameters.push(data[i]);
      }
    }
  }
}
