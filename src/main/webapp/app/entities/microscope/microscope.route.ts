import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMicroscope, Microscope } from 'app/shared/model/microscope.model';
import { MicroscopeService } from './microscope.service';
import { MicroscopeComponent } from './microscope.component';
import { MicroscopeDetailComponent } from './microscope-detail.component';
import { MicroscopeUpdateComponent } from './microscope-update.component';

@Injectable({ providedIn: 'root' })
export class MicroscopeResolve implements Resolve<IMicroscope> {
  constructor(private service: MicroscopeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMicroscope> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((microscope: HttpResponse<Microscope>) => {
          if (microscope.body) {
            return of(microscope.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Microscope());
  }
}

export const microscopeRoute: Routes = [
  {
    path: '',
    component: MicroscopeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.microscope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MicroscopeDetailComponent,
    resolve: {
      microscope: MicroscopeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.microscope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MicroscopeUpdateComponent,
    resolve: {
      microscope: MicroscopeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.microscope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MicroscopeUpdateComponent,
    resolve: {
      microscope: MicroscopeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.microscope.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
