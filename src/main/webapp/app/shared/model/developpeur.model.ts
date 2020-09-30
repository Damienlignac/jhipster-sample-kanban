import { Moment } from 'moment';
import { IProjet } from 'app/shared/model/projet.model';
import { ITache } from 'app/shared/model/tache.model';

export interface IDeveloppeur {
  id?: number;
  nom?: string;
  prenom?: string;
  dtNaissance?: Moment;
  email?: string;
  nbTachesEnCours?: number;
  numeroCarteBleue?: string;
  projets?: IProjet[];
  taches?: ITache[];
}

export class Developpeur implements IDeveloppeur {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public dtNaissance?: Moment,
    public email?: string,
    public nbTachesEnCours?: number,
    public numeroCarteBleue?: string,
    public projets?: IProjet[],
    public taches?: ITache[]
  ) {}
}
