import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RulesValidatorSharedModule } from 'app/shared/shared.module';
import { RvRuleGroupComponent } from './rv-rule-group.component';
import { RvRuleGroupDetailComponent } from './rv-rule-group-detail.component';
import { RvRuleGroupUpdateComponent } from './rv-rule-group-update.component';
import { RvRuleGroupDeleteDialogComponent } from './rv-rule-group-delete-dialog.component';
import { rvRuleGroupRoute } from './rv-rule-group.route';

@NgModule({
  imports: [RulesValidatorSharedModule, RouterModule.forChild(rvRuleGroupRoute)],
  declarations: [RvRuleGroupComponent, RvRuleGroupDetailComponent, RvRuleGroupUpdateComponent, RvRuleGroupDeleteDialogComponent],
  entryComponents: [RvRuleGroupDeleteDialogComponent],
})
export class RulesValidatorRvRuleGroupModule {}
