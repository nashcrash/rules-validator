import { IParameter } from 'app/shared/model/parameter.model';
import { RuleMode } from 'app/shared/model/enumerations/rule-mode.model';

export interface IRule {
  id?: number;
  ruleCode?: string;
  description?: string;
  mode?: RuleMode;
  operatorId?: number;
  parameters?: IParameter[];
}

export class Rule implements IRule {
  constructor(
    public id?: number,
    public ruleCode?: string,
    public description?: string,
    public mode?: RuleMode,
    public operatorId?: number,
    public parameters?: IParameter[]
  ) {}
}
