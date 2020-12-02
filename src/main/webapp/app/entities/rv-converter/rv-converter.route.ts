import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRvConverter, RvConverter } from 'app/shared/model/rv-converter.model';
import { RvConverterService } from './rv-converter.service';
import { RvConverterComponent } from './rv-converter.component';
import { RvConverterDetailComponent } from './rv-converter-detail.component';
import { RvConverterUpdateComponent } from './rv-converter-update.component';

@Injectable({ providedIn: 'root' })
export class RvConverterResolve implements Resolve<IRvConverter> {
  constructor(private service: RvConverterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRvConverter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rvConverter: HttpResponse<RvConverter>) => {
          if (rvConverter.body) {
            return of(rvConverter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RvConverter());
  }
}

export const rvConverterRoute: Routes = [
  {
    path: '',
    component: RvConverterComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvConverter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RvConverterDetailComponent,
    resolve: {
      rvConverter: RvConverterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvConverter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RvConverterUpdateComponent,
    resolve: {
      rvConverter: RvConverterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvConverter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RvConverterUpdateComponent,
    resolve: {
      rvConverter: RvConverterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.rvConverter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
