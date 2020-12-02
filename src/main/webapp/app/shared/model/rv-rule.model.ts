import { IRvParam } from 'app/shared/model/rv-param.model';
import { RvRuleLevel } from 'app/shared/model/enumerations/rv-rule-level.model';
import { RvRuleMode } from 'app/shared/model/enumerations/rv-rule-mode.model';
import { IRvOperator } from 'app/shared/model/rv-operator.model';

export interface IRvRule {
  id?: number;
  ruleCode?: string;
  description?: string;
  level?: RvRuleLevel;
  mode?: RvRuleMode;
  groupId?: number;
  operator?: IRvOperator;
  rvParams?: IRvParam[];
}

export class RvRule implements IRvRule {
  constructor(
    public id?: number,
    public ruleCode?: string,
    public description?: string,
    public level?: RvRuleLevel,
    public mode?: RvRuleMode,
    public groupId?: number,
    public operator?: IRvOperator,
    public rvParams?: IRvParam[]
  ) {}
}
