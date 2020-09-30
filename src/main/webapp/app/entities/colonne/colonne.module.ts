import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KanBanSharedModule } from 'app/shared/shared.module';
import { ColonneComponent } from './colonne.component';
import { ColonneDetailComponent } from './colonne-detail.component';
import { ColonneUpdateComponent } from './colonne-update.component';
import { ColonneDeleteDialogComponent } from './colonne-delete-dialog.component';
import { colonneRoute } from './colonne.route';

@NgModule({
  imports: [KanBanSharedModule, RouterModule.forChild(colonneRoute)],
  declarations: [ColonneComponent, ColonneDetailComponent, ColonneUpdateComponent, ColonneDeleteDialogComponent],
  entryComponents: [ColonneDeleteDialogComponent],
})
export class KanBanColonneModule {}
