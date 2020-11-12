import { IConverter } from 'app/shared/model/converter.model';
import { IRule } from 'app/shared/model/rule.model';

export interface IParam {
  id?: number;
  value?: string;
  converters?: IConverter[];
  rules?: IRule[];
}

export class Param implements IParam {
  constructor(public id?: number, public value?: string, public converters?: IConverter[], public rules?: IRule[]) {}
}
