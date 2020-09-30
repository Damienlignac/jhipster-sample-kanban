import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KanBanSharedModule } from 'app/shared/shared.module';
import { DeveloppeurComponent } from './developpeur.component';
import { DeveloppeurDetailComponent } from './developpeur-detail.component';
import { DeveloppeurUpdateComponent } from './developpeur-update.component';
import { DeveloppeurDeleteDialogComponent } from './developpeur-delete-dialog.component';
import { developpeurRoute } from './developpeur.route';

@NgModule({
  imports: [KanBanSharedModule, RouterModule.forChild(developpeurRoute)],
  declarations: [DeveloppeurComponent, DeveloppeurDetailComponent, DeveloppeurUpdateComponent, DeveloppeurDeleteDialogComponent],
  entryComponents: [DeveloppeurDeleteDialogComponent],
})
export class KanBanDeveloppeurModule {}
