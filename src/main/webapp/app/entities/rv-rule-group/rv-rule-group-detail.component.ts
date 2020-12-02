import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRvRuleGroup } from 'app/shared/model/rv-rule-group.model';

@Component({
  selector: 'jhi-rv-rule-group-detail',
  templateUrl: './rv-rule-group-detail.component.html',
})
export class RvRuleGroupDetailComponent implements OnInit {
  rvRuleGroup: IRvRuleGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvRuleGroup }) => (this.rvRuleGroup = rvRuleGroup));
  }

  previousState(): void {
    window.history.back();
  }
}
