export interface IRvRuleGroup {
  id?: number;
  ruleGroupName?: string;
}

export class RvRuleGroup implements IRvRuleGroup {
  constructor(public id?: number, public ruleGroupName?: string) {}
}
