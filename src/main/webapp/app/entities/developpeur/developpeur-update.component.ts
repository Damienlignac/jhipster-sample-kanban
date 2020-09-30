import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDeveloppeur, Developpeur } from 'app/shared/model/developpeur.model';
import { DeveloppeurService } from './developpeur.service';
import { IProjet } from 'app/shared/model/projet.model';
import { ProjetService } from 'app/entities/projet/projet.service';

@Component({
  selector: 'jhi-developpeur-update',
  templateUrl: './developpeur-update.component.html',
})
export class DeveloppeurUpdateComponent implements OnInit {
  isSaving = false;
  projets: IProjet[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    prenom: [],
    dtNaissance: [],
    email: [null, []],
    nbTachesEnCours: [],
    numeroCarteBleue: [],
    projets: [],
  });

  constructor(
    protected developpeurService: DeveloppeurService,
    protected projetService: ProjetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ developpeur }) => {
      if (!developpeur.id) {
        const today = moment().startOf('day');
        developpeur.dtNaissance = today;
      }

      this.updateForm(developpeur);

      this.projetService.query().subscribe((res: HttpResponse<IProjet[]>) => (this.projets = res.body || []));
    });
  }

  updateForm(developpeur: IDeveloppeur): void {
    this.editForm.patchValue({
      id: developpeur.id,
      nom: developpeur.nom,
      prenom: developpeur.prenom,
      dtNaissance: developpeur.dtNaissance ? developpeur.dtNaissance.format(DATE_TIME_FORMAT) : null,
      email: developpeur.email,
      nbTachesEnCours: developpeur.nbTachesEnCours,
      numeroCarteBleue: developpeur.numeroCarteBleue,
      projets: developpeur.projets,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const developpeur = this.createFromForm();
    if (developpeur.id !== undefined) {
      this.subscribeToSaveResponse(this.developpeurService.update(developpeur));
    } else {
      this.subscribeToSaveResponse(this.developpeurService.create(developpeur));
    }
  }

  private createFromForm(): IDeveloppeur {
    return {
      ...new Developpeur(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      dtNaissance: this.editForm.get(['dtNaissance'])!.value
        ? moment(this.editForm.get(['dtNaissance'])!.value, DATE_TIME_FORMAT)
        : undefined,
      email: this.editForm.get(['email'])!.value,
      nbTachesEnCours: this.editForm.get(['nbTachesEnCours'])!.value,
      numeroCarteBleue: this.editForm.get(['numeroCarteBleue'])!.value,
      projets: this.editForm.get(['projets'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeveloppeur>>): void {
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

  trackById(index: number, item: IProjet): any {
    return item.id;
  }

  getSelected(selectedVals: IProjet[], option: IProjet): IProjet {
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
