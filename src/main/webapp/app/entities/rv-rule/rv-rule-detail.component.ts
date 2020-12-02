import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRvRule } from 'app/shared/model/rv-rule.model';

@Component({
  selector: 'jhi-rv-rule-detail',
  templateUrl: './rv-rule-detail.component.html',
})
export class RvRuleDetailComponent implements OnInit {
  rvRule: IRvRule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvRule }) => (this.rvRule = rvRule));
  }

  previousState(): void {
    window.history.back();
  }
}
