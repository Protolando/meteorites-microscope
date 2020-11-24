import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MeteoritesMicroscopeSharedModule } from 'app/shared/shared.module';
import { MicroscopeComponent } from './microscope.component';
import { MicroscopeDetailComponent } from './microscope-detail.component';
import { MicroscopeUpdateComponent } from './microscope-update.component';
import { MicroscopeDeleteDialogComponent } from './microscope-delete-dialog.component';
import { microscopeRoute } from './microscope.route';

@NgModule({
  imports: [MeteoritesMicroscopeSharedModule, RouterModule.forChild(microscopeRoute)],
  declarations: [MicroscopeComponent, MicroscopeDetailComponent, MicroscopeUpdateComponent, MicroscopeDeleteDialogComponent],
  entryComponents: [MicroscopeDeleteDialogComponent],
})
export class MeteoritesMicroscopeMicroscopeModule {}
