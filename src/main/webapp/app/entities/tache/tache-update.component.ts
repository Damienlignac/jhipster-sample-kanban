import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITache, Tache } from 'app/shared/model/tache.model';
import { TacheService } from './tache.service';
import { IDeveloppeur } from 'app/shared/model/developpeur.model';
import { DeveloppeurService } from 'app/entities/developpeur/developpeur.service';
import { IColonne } from 'app/shared/model/colonne.model';
import { ColonneService } from 'app/entities/colonne/colonne.service';
import { ITypeTache } from 'app/shared/model/type-tache.model';
import { TypeTacheService } from 'app/entities/type-tache/type-tache.service';

type SelectableEntity = IDeveloppeur | IColonne | ITypeTache;

@Component({
  selector: 'jhi-tache-update',
  templateUrl: './tache-update.component.html',
})
export class TacheUpdateComponent implements OnInit {
  isSaving = false;
  developpeurs: IDeveloppeur[] = [];
  colonnes: IColonne[] = [];
  typetaches: ITypeTache[] = [];

  editForm = this.fb.group({
    id: [],
    intitule: [],
    dtCreation: [],
    nbHeuresEstimees: [],
    nbHeuresReelles: [],
    developpeurs: [],
    colonne: [],
    typeTache: [],
  });

  constructor(
    protected tacheService: TacheService,
    protected developpeurService: DeveloppeurService,
    protected colonneService: ColonneService,
    protected typeTacheService: TypeTacheService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tache }) => {
      if (!tache.id) {
        const today = moment().startOf('day');
        tache.dtCreation = today;
      }

      this.updateForm(tache);

      this.developpeurService.query().subscribe((res: HttpResponse<IDeveloppeur[]>) => (this.developpeurs = res.body || []));

      this.colonneService.query().subscribe((res: HttpResponse<IColonne[]>) => (this.colonnes = res.body || []));

      this.typeTacheService.query().subscribe((res: HttpResponse<ITypeTache[]>) => (this.typetaches = res.body || []));
    });
  }

  updateForm(tache: ITache): void {
    this.editForm.patchValue({
      id: tache.id,
      intitule: tache.intitule,
      dtCreation: tache.dtCreation ? tache.dtCreation.format(DATE_TIME_FORMAT) : null,
      nbHeuresEstimees: tache.nbHeuresEstimees,
      nbHeuresReelles: tache.nbHeuresReelles,
      developpeurs: tache.developpeurs,
      colonne: tache.colonne,
      typeTache: tache.typeTache,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tache = this.createFromForm();
    if (tache.id !== undefined) {
      this.subscribeToSaveResponse(this.tacheService.update(tache));
    } else {
      this.subscribeToSaveResponse(this.tacheService.create(tache));
    }
  }

  private createFromForm(): ITache {
    return {
      ...new Tache(),
      id: this.editForm.get(['id'])!.value,
      intitule: this.editForm.get(['intitule'])!.value,
      dtCreation: this.editForm.get(['dtCreation'])!.value ? moment(this.editForm.get(['dtCreation'])!.value, DATE_TIME_FORMAT) : undefined,
      nbHeuresEstimees: this.editForm.get(['nbHeuresEstimees'])!.value,
      nbHeuresReelles: this.editForm.get(['nbHeuresReelles'])!.value,
      developpeurs: this.editForm.get(['developpeurs'])!.value,
      colonne: this.editForm.get(['colonne'])!.value,
      typeTache: this.editForm.get(['typeTache'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITache>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IDeveloppeur[], option: IDeveloppeur): IDeveloppeur {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
