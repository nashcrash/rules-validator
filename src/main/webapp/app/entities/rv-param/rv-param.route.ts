import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRvParam, RvParam } from 'app/shared/model/rv-param.model';
import { RvParamService } from './rv-param.service';
import { RvParamComponent } from './rv-param.component';
import { RvParamDetailComponent } from './rv-param-detail.component';
import { RvParamUpdateComponent } from './rv-param-update.component';

@Injectable({ providedIn: 'root' })
export class RvParamResolve implements Resolve<IRvParam> {
  constructor(private service: RvParamService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRvParam> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rvParam: HttpResponse<RvParam>) => {
          if (rvParam.body) {
            return of(rvParam.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RvParam());
  }
}

export const rvParamRoute: Routes = [
  {
    path: '',
    component: RvParamComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvParam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RvParamDetailComponent,
    resolve: {
      rvParam: RvParamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvParam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RvParamUpdateComponent,
    resolve: {
      rvParam: RvParamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvParam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RvParamUpdateComponent,
    resolve: {
      rvParam: RvParamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvParam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
