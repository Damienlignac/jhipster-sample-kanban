import { ITache } from 'app/shared/model/tache.model';

export interface ITypeTache {
  id?: number;
  nom?: string;
  description?: string;
  couleur?: string;
  taches?: ITache[];
}

export class TypeTache implements ITypeTache {
  constructor(public id?: number, public nom?: string, public description?: string, public couleur?: string, public taches?: ITache[]) {}
}
