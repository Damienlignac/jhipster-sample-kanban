import { Moment } from 'moment';
import { IDeveloppeur } from 'app/shared/model/developpeur.model';
import { IColonne } from 'app/shared/model/colonne.model';
import { ITypeTache } from 'app/shared/model/type-tache.model';

export interface ITache {
  id?: number;
  intitule?: string;
  dtCreation?: Moment;
  nbHeuresEstimees?: number;
  nbHeuresReelles?: number;
  developpeurs?: IDeveloppeur[];
  colonne?: IColonne;
  typeTache?: ITypeTache;
}

export class Tache implements ITache {
  constructor(
    public id?: number,
    public intitule?: string,
    public dtCreation?: Moment,
    public nbHeuresEstimees?: number,
    public nbHeuresReelles?: number,
    public developpeurs?: IDeveloppeur[],
    public colonne?: IColonne,
    public typeTache?: ITypeTache
  ) {}
}
