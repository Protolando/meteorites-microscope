import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'rock',
        loadChildren: () => import('./rock/rock.module').then(m => m.MeteoritesMicroscopeRockModule),
      },
      {
        path: 'microscope',
        loadChildren: () => import('./microscope/microscope.module').then(m => m.MeteoritesMicroscopeMicroscopeModule),
      },
      {
        path: 'microscope-picture',
        loadChildren: () =>
          import('./microscope-picture/microscope-picture.module').then(m => m.MeteoritesMicroscopeMicroscopePictureModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MeteoritesMicroscopeEntityModule {}
