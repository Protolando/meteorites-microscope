import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MeteoritesMicroscopeSharedModule } from 'app/shared/shared.module';
import { MicroscopePictureComponent } from './microscope-picture.component';
import { MicroscopePictureDetailComponent } from './microscope-picture-detail.component';
import { MicroscopePictureUpdateComponent } from './microscope-picture-update.component';
import { MicroscopePictureDeleteDialogComponent } from './microscope-picture-delete-dialog.component';
import { microscopePictureRoute } from './microscope-picture.route';

@NgModule({
  imports: [MeteoritesMicroscopeSharedModule, RouterModule.forChild(microscopePictureRoute)],
  declarations: [
    MicroscopePictureComponent,
    MicroscopePictureDetailComponent,
    MicroscopePictureUpdateComponent,
    MicroscopePictureDeleteDialogComponent,
  ],
  entryComponents: [MicroscopePictureDeleteDialogComponent],
})
export class MeteoritesMicroscopeMicroscopePictureModule {}
