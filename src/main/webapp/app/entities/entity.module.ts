import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ville',
        loadChildren: () => import('./ville/ville.module').then(m => m.KanBanVilleModule),
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.KanBanClientModule),
      },
      {
        path: 'projet',
        loadChildren: () => import('./projet/projet.module').then(m => m.KanBanProjetModule),
      },
      {
        path: 'type-tache',
        loadChildren: () => import('./type-tache/type-tache.module').then(m => m.KanBanTypeTacheModule),
      },
      {
        path: 'tache',
        loadChildren: () => import('./tache/tache.module').then(m => m.KanBanTacheModule),
      },
      {
        path: 'colonne',
        loadChildren: () => import('./colonne/colonne.module').then(m => m.KanBanColonneModule),
      },
      {
        path: 'developpeur',
        loadChildren: () => import('./developpeur/developpeur.module').then(m => m.KanBanDeveloppeurModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class KanBanEntityModule {}
