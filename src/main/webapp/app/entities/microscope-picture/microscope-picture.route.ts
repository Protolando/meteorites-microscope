import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMicroscopePicture, MicroscopePicture } from 'app/shared/model/microscope-picture.model';
import { MicroscopePictureService } from './microscope-picture.service';
import { MicroscopePictureComponent } from './microscope-picture.component';
import { MicroscopePictureDetailComponent } from './microscope-picture-detail.component';
import { MicroscopePictureUpdateComponent } from './microscope-picture-update.component';

@Injectable({ providedIn: 'root' })
export class MicroscopePictureResolve implements Resolve<IMicroscopePicture> {
  constructor(private service: MicroscopePictureService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMicroscopePicture> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((microscopePicture: HttpResponse<MicroscopePicture>) => {
          if (microscopePicture.body) {
            return of(microscopePicture.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MicroscopePicture());
  }
}

export const microscopePictureRoute: Routes = [
  {
    path: '',
    component: MicroscopePictureComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.microscopePicture.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MicroscopePictureDetailComponent,
    resolve: {
      microscopePicture: MicroscopePictureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.microscopePicture.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MicroscopePictureUpdateComponent,
    resolve: {
      microscopePicture: MicroscopePictureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.microscopePicture.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MicroscopePictureUpdateComponent,
    resolve: {
      microscopePicture: MicroscopePictureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'meteoritesMicroscopeApp.microscopePicture.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
