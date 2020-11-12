import { IParameter } from 'app/shared/model/parameter.model';

export interface IConverter {
  id?: number;
  converterCode?: string;
  description?: string;
  beanName?: string;
  parameters?: IParameter[];
}

export class Converter implements IConverter {
  constructor(
    public id?: number,
    public converterCode?: string,
    public description?: string,
    public beanName?: string,
    public parameters?: IParameter[]
  ) {}
}
