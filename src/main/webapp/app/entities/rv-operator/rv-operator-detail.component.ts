import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRvOperator } from 'app/shared/model/rv-operator.model';

@Component({
  selector: 'jhi-rv-operator-detail',
  templateUrl: './rv-operator-detail.component.html',
})
export class RvOperatorDetailComponent implements OnInit {
  rvOperator: IRvOperator | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvOperator }) => (this.rvOperator = rvOperator));
  }

  previousState(): void {
    window.history.back();
  }
}
