import { IRvOperatorParam } from 'app/shared/model/rv-operator-param.model';

export interface IRvOperator {
  id?: number;
  operatorCode?: string;
  description?: string;
  beanName?: string;
  numberOfParams?: number;
  params?: IRvOperatorParam[];
}

export class RvOperator implements IRvOperator {
  constructor(
    public id?: number,
    public operatorCode?: string,
    public description?: string,
    public beanName?: string,
    public numberOfParams?: number,
    public params?: IRvOperatorParam[]
  ) {}
}
