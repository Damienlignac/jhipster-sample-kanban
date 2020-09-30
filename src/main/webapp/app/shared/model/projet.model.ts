import { Moment } from 'moment';
import { IClient } from 'app/shared/model/client.model';
import { IDeveloppeur } from 'app/shared/model/developpeur.model';

export interface IProjet {
  id?: number;
  nom?: string;
  dtNaissance?: Moment;
  dtLivraison?: Moment;
  client?: IClient;
  developpeurs?: IDeveloppeur[];
}

export class Projet implements IProjet {
  constructor(
    public id?: number,
    public nom?: string,
    public dtNaissance?: Moment,
    public dtLivraison?: Moment,
    public client?: IClient,
    public developpeurs?: IDeveloppeur[]
  ) {}
}
