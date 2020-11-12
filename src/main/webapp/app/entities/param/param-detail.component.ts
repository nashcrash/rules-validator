import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParam } from 'app/shared/model/param.model';

@Component({
  selector: 'jhi-param-detail',
  templateUrl: './param-detail.component.html',
})
export class ParamDetailComponent implements OnInit {
  param: IParam | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ param }) => (this.param = param));
  }

  previousState(): void {
    window.history.back();
  }
}
