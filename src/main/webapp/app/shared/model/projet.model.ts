import { Moment } from 'moment';
import { IDeveloppeur } from 'app/shared/model/developpeur.model';
import { IClient } from 'app/shared/model/client.model';

export interface IProjet {
  id?: number;
  nom?: string;
  dtNaissance?: Moment;
  dtLivraison?: Moment;
  developpeurs?: IDeveloppeur[];
  client?: IClient;
  developpeurs?: IDeveloppeur[];
}

export class Projet implements IProjet {
  constructor(
    public id?: number,
    public nom?: string,
    public dtNaissance?: Moment,
    public dtLivraison?: Moment,
    public developpeurs?: IDeveloppeur[],
    public client?: IClient,
    public developpeurs?: IDeveloppeur[]
  ) {}
}
