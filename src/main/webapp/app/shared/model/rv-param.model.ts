import { IRvConverter } from 'app/shared/model/rv-converter.model';
import { IRvRule } from 'app/shared/model/rv-rule.model';

export interface IRvParam {
  id?: number;
  value?: string;
  rvConverters?: IRvConverter[];
  rvRules?: IRvRule[];
}

export class RvParam implements IRvParam {
  constructor(public id?: number, public value?: string, public rvConverters?: IRvConverter[], public rvRules?: IRvRule[]) {}
}
