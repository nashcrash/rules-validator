import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRvOperatorParam, RvOperatorParam } from 'app/shared/model/rv-operator-param.model';
import { RvOperatorParamService } from './rv-operator-param.service';
import { RvOperatorParamComponent } from './rv-operator-param.component';
import { RvOperatorParamDetailComponent } from './rv-operator-param-detail.component';
import { RvOperatorParamUpdateComponent } from './rv-operator-param-update.component';

@Injectable({ providedIn: 'root' })
export class RvOperatorParamResolve implements Resolve<IRvOperatorParam> {
  constructor(private service: RvOperatorParamService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRvOperatorParam> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rvOperatorParam: HttpResponse<RvOperatorParam>) => {
          if (rvOperatorParam.body) {
            return of(rvOperatorParam.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RvOperatorParam());
  }
}

export const rvOperatorParamRoute: Routes = [
  {
    path: '',
    component: RvOperatorParamComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvOperatorParam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RvOperatorParamDetailComponent,
    resolve: {
      rvOperatorParam: RvOperatorParamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvOperatorParam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RvOperatorParamUpdateComponent,
    resolve: {
      rvOperatorParam: RvOperatorParamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvOperatorParam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RvOperatorParamUpdateComponent,
    resolve: {
      rvOperatorParam: RvOperatorParamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvOperatorParam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
