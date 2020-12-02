import { RvParamType } from 'app/shared/model/enumerations/rv-param-type.model';

export interface IRvOperatorParam {
  id?: number;
  name?: string;
  description?: string;
  type?: RvParamType;
  operatorId?: number;
}

export class RvOperatorParam implements IRvOperatorParam {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public type?: RvParamType,
    public operatorId?: number
  ) {}
}
