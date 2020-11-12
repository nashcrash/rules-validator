import { IParam } from 'app/shared/model/param.model';

export interface IConverter {
  id?: number;
  converterCode?: string;
  description?: string;
  beanName?: string;
  params?: IParam[];
}

export class Converter implements IConverter {
  constructor(
    public id?: number,
    public converterCode?: string,
    public description?: string,
    public beanName?: string,
    public params?: IParam[]
  ) {}
}
