import { IRvParam } from 'app/shared/model/rv-param.model';
import { RvRuleLevel } from 'app/shared/model/enumerations/rv-rule-level.model';
import { RvRuleMode } from 'app/shared/model/enumerations/rv-rule-mode.model';
import { IRvOperator } from 'app/shared/model/rv-operator.model';
import { IRvRuleGroup } from 'app/shared/model/rv-rule-group.model';

export interface IRvRule {
  id?: number;
  ruleCode?: string;
  description?: string;
  level?: RvRuleLevel;
  mode?: RvRuleMode;
  group?: IRvRuleGroup;
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
    public group?: IRvRuleGroup,
    public operator?: IRvOperator,
    public rvParams?: IRvParam[]
  ) {}
}
