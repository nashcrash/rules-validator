import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { RuleEditorComponent } from './rule-editor.component';
import { PAGE_ONE_ROUTE } from './page-one/page-one.route';
import { PAGE_TWO_ROUTE } from './page-two/page-two.route';

export const RULE_EDITOR_ROUTE: Route = {
  path: 'rule-editor',
  component: RuleEditorComponent,
  data: {
    authorities: [],
    pageTitle: 'rule-editor.title',
  },
  canActivate: [UserRouteAccessService],
  children: [PAGE_ONE_ROUTE, PAGE_TWO_ROUTE],
};
