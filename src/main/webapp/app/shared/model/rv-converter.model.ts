import { IRvParam } from 'app/shared/model/rv-param.model';

export interface IRvConverter {
  id?: number;
  converterCode?: string;
  description?: string;
  beanName?: string;
  rvParams?: IRvParam[];
}

export class RvConverter implements IRvConverter {
  constructor(
    public id?: number,
    public converterCode?: string,
    public description?: string,
    public beanName?: string,
    public rvParams?: IRvParam[]
  ) {}
}
