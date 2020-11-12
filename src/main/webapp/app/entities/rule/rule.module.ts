import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ValidationRulesSharedModule } from 'app/shared/shared.module';
import { RuleComponent } from './rule.component';
import { RuleDetailComponent } from './rule-detail.component';
import { RuleUpdateComponent } from './rule-update.component';
import { RuleDeleteDialogComponent } from './rule-delete-dialog.component';
import { ruleRoute } from './rule.route';

@NgModule({
  imports: [ValidationRulesSharedModule, RouterModule.forChild(ruleRoute)],
  declarations: [RuleComponent, RuleDetailComponent, RuleUpdateComponent, RuleDeleteDialogComponent],
  entryComponents: [RuleDeleteDialogComponent],
})
export class ValidationRulesRuleModule {}