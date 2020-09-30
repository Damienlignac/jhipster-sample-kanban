import { IClient } from 'app/shared/model/client.model';

export interface IVille {
  id?: number;
  nom?: string;
  codePostal?: string;
  clients?: IClient[];
}

export class Ville implements IVille {
  constructor(public id?: number, public nom?: string, public codePostal?: string, public clients?: IClient[]) {}
}
