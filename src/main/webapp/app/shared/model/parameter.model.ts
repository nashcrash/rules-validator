import { IConverter } from 'app/shared/model/converter.model';
import { IRule } from 'app/shared/model/rule.model';

export interface IParameter {
  id?: number;
  value?: string;
  converters?: IConverter[];
  rules?: IRule[];
}

export class Parameter implements IParameter {
  constructor(public id?: number, public value?: string, public converters?: IConverter[], public rules?: IRule[]) {}
}
