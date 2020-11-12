import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IParam, Param } from 'app/shared/model/param.model';
import { ParamService } from './param.service';
import { ParamComponent } from './param.component';
import { ParamDetailComponent } from './param-detail.component';
import { ParamUpdateComponent } from './param-update.component';

@Injectable({ providedIn: 'root' })
export class ParamResolve implements Resolve<IParam> {
  constructor(private service: ParamService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParam> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((param: HttpResponse<Param>) => {
          if (param.body) {
            return of(param.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Param());
  }
}

export const paramRoute: Routes = [
  {
    path: '',
    component: ParamComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.param.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParamDetailComponent,
    resolve: {
      param: ParamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.param.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParamUpdateComponent,
    resolve: {
      param: ParamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.param.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParamUpdateComponent,
    resolve: {
      param: ParamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.param.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
