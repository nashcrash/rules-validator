import { RvRuleLevel } from 'app/shared/model/enumerations/rv-rule-level.model';

export class ValidationRequest {
  constructor(public type?: string, public ruleCode?: string, public model?: string) {}
}

export class ValidationResult {
  constructor(public valid?: boolean, public details?: ValidationResultDetail[]) {}
}

export class ValidationResultDetail {
  constructor(
    public ruleCode?: string,
    public level?: RvRuleLevel,
    public operatorCode?: string,
    public description?: string,
    public message?: string,
    public attributes?: string[]
  ) {}
}
