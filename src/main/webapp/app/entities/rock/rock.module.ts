import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MeteoritesMicroscopeSharedModule } from 'app/shared/shared.module';
import { RockComponent } from './rock.component';
import { RockDetailComponent } from './rock-detail.component';
import { RockUpdateComponent } from './rock-update.component';
import { RockDeleteDialogComponent } from './rock-delete-dialog.component';
import { rockRoute } from './rock.route';

@NgModule({
  imports: [MeteoritesMicroscopeSharedModule, RouterModule.forChild(rockRoute)],
  declarations: [RockComponent, RockDetailComponent, RockUpdateComponent, RockDeleteDialogComponent],
  entryComponents: [RockDeleteDialogComponent],
})
export class MeteoritesMicroscopeRockModule {}
