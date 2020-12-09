import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ValidationTesterComponent } from './validation-tester.component';

export const VALIDATION_TESTER_ROUTE: Route = {
  path: 'validation-tester',
  component: ValidationTesterComponent,
  data: {
    authorities: [],
    pageTitle: 'validation-tester.title',
  },
  canActivate: [UserRouteAccessService],
};
