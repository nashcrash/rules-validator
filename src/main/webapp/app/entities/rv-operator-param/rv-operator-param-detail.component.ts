import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRvOperatorParam } from 'app/shared/model/rv-operator-param.model';

@Component({
  selector: 'jhi-rv-operator-param-detail',
  templateUrl: './rv-operator-param-detail.component.html',
})
export class RvOperatorParamDetailComponent implements OnInit {
  rvOperatorParam: IRvOperatorParam | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvOperatorParam }) => (this.rvOperatorParam = rvOperatorParam));
  }

  previousState(): void {
    window.history.back();
  }
}
