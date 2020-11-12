import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConverter, Converter } from 'app/shared/model/converter.model';
import { ConverterService } from './converter.service';
import { ConverterComponent } from './converter.component';
import { ConverterDetailComponent } from './converter-detail.component';
import { ConverterUpdateComponent } from './converter-update.component';

@Injectable({ providedIn: 'root' })
export class ConverterResolve implements Resolve<IConverter> {
  constructor(private service: ConverterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConverter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((converter: HttpResponse<Converter>) => {
          if (converter.body) {
            return of(converter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Converter());
  }
}

export const converterRoute: Routes = [
  {
    path: '',
    component: ConverterComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.converter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConverterDetailComponent,
    resolve: {
      converter: ConverterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.converter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConverterUpdateComponent,
    resolve: {
      converter: ConverterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.converter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConverterUpdateComponent,
    resolve: {
      converter: ConverterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'rulesValidatorApp.converter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
