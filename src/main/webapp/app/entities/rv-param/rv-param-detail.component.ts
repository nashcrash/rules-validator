import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRvParam } from 'app/shared/model/rv-param.model';

@Component({
  selector: 'jhi-rv-param-detail',
  templateUrl: './rv-param-detail.component.html',
})
export class RvParamDetailComponent implements OnInit {
  rvParam: IRvParam | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvParam }) => (this.rvParam = rvParam));
  }

  previousState(): void {
    window.history.back();
  }
}
