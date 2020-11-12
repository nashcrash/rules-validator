import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IParameter, Parameter } from 'app/shared/model/parameter.model';
import { ParameterService } from './parameter.service';
import { ParameterComponent } from './parameter.component';
import { ParameterDetailComponent } from './parameter-detail.component';
import { ParameterUpdateComponent } from './parameter-update.component';

@Injectable({ providedIn: 'root' })
export class ParameterResolve implements Resolve<IParameter> {
  constructor(private service: ParameterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParameter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((parameter: HttpResponse<Parameter>) => {
          if (parameter.body) {
            return of(parameter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Parameter());
  }
}

export const parameterRoute: Routes = [
  {
    path: '',
    component: ParameterComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.parameter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParameterDetailComponent,
    resolve: {
      parameter: ParameterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.parameter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParameterUpdateComponent,
    resolve: {
      parameter: ParameterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.parameter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParameterUpdateComponent,
    resolve: {
      parameter: ParameterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.parameter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
