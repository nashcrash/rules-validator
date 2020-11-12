export interface IRvOperator {
  id?: number;
  operatorCode?: string;
  description?: string;
  beanName?: string;
  numberOfParams?: number;
}

export class RvOperator implements IRvOperator {
  constructor(
    public id?: number,
    public operatorCode?: string,
    public description?: string,
    public beanName?: string,
    public numberOfParams?: number
  ) {}
}
