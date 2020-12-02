import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRvRule, RvRule } from 'app/shared/model/rv-rule.model';
import { RvRuleService } from './rv-rule.service';
import { RvRuleComponent } from './rv-rule.component';
import { RvRuleDetailComponent } from './rv-rule-detail.component';
import { RvRuleUpdateComponent } from './rv-rule-update.component';

@Injectable({ providedIn: 'root' })
export class RvRuleResolve implements Resolve<IRvRule> {
  constructor(private service: RvRuleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRvRule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rvRule: HttpResponse<RvRule>) => {
          if (rvRule.body) {
            return of(rvRule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RvRule());
  }
}

export const rvRuleRoute: Routes = [
  {
    path: '',
    component: RvRuleComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvRule.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RvRuleDetailComponent,
    resolve: {
      rvRule: RvRuleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvRule.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RvRuleUpdateComponent,
    resolve: {
      rvRule: RvRuleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvRule.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RvRuleUpdateComponent,
    resolve: {
      rvRule: RvRuleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvRule.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
