import { IRvParam } from 'app/shared/model/rv-param.model';
import { RvRuleMode } from 'app/shared/model/enumerations/rv-rule-mode.model';

export interface IRvRule {
  id?: number;
  ruleCode?: string;
  description?: string;
  mode?: RvRuleMode;
  operatorId?: number;
  rvParams?: IRvParam[];
}

export class RvRule implements IRvRule {
  constructor(
    public id?: number,
    public ruleCode?: string,
    public description?: string,
    public mode?: RvRuleMode,
    public operatorId?: number,
    public rvParams?: IRvParam[]
  ) {}
}
