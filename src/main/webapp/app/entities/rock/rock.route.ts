import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRock, Rock } from 'app/shared/model/rock.model';
import { RockService } from './rock.service';
import { RockComponent } from './rock.component';
import { RockDetailComponent } from './rock-detail.component';
import { RockUpdateComponent } from './rock-update.component';

@Injectable({ providedIn: 'root' })
export class RockResolve implements Resolve<IRock> {
  constructor(private service: RockService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rock: HttpResponse<Rock>) => {
          if (rock.body) {
            return of(rock.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Rock());
  }
}

export const rockRoute: Routes = [
  {
    path: '',
    component: RockComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.rock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RockDetailComponent,
    resolve: {
      rock: RockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.rock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RockUpdateComponent,
    resolve: {
      rock: RockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.rock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RockUpdateComponent,
    resolve: {
      rock: RockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.rock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
