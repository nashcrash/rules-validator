import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRvOperator, RvOperator } from 'app/shared/model/rv-operator.model';
import { RvOperatorService } from './rv-operator.service';
import { RvOperatorComponent } from './rv-operator.component';
import { RvOperatorDetailComponent } from './rv-operator-detail.component';
import { RvOperatorUpdateComponent } from './rv-operator-update.component';

@Injectable({ providedIn: 'root' })
export class RvOperatorResolve implements Resolve<IRvOperator> {
  constructor(private service: RvOperatorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRvOperator> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rvOperator: HttpResponse<RvOperator>) => {
          if (rvOperator.body) {
            return of(rvOperator.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RvOperator());
  }
}

export const rvOperatorRoute: Routes = [
  {
    path: '',
    component: RvOperatorComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvOperator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RvOperatorDetailComponent,
    resolve: {
      rvOperator: RvOperatorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvOperator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RvOperatorUpdateComponent,
    resolve: {
      rvOperator: RvOperatorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvOperator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RvOperatorUpdateComponent,
    resolve: {
      rvOperator: RvOperatorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvOperator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
