import { IParam } from 'app/shared/model/param.model';
import { RuleMode } from 'app/shared/model/enumerations/rule-mode.model';

export interface IRule {
  id?: number;
  ruleCode?: string;
  description?: string;
  mode?: RuleMode;
  operatorId?: number;
  params?: IParam[];
}

export class Rule implements IRule {
  constructor(
    public id?: number,
    public ruleCode?: string,
    public description?: string,
    public mode?: RuleMode,
    public operatorId?: number,
    public params?: IParam[]
  ) {}
}
