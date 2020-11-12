export interface IOperator {
  id?: number;
  operatorCode?: string;
  description?: string;
  beanName?: string;
  numberOfParams?: number;
}

export class Operator implements IOperator {
  constructor(
    public id?: number,
    public operatorCode?: string,
    public description?: string,
    public beanName?: string,
    public numberOfParams?: number
  ) {}
}
