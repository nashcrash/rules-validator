import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RulesValidatorSharedModule } from 'app/shared/shared.module';
import { RvRuleComponent } from './rv-rule.component';
import { RvRuleDetailComponent } from './rv-rule-detail.component';
import { RvRuleUpdateComponent } from './rv-rule-update.component';
import { RvRuleDeleteDialogComponent } from './rv-rule-delete-dialog.component';
import { rvRuleRoute } from './rv-rule.route';

@NgModule({
  imports: [RulesValidatorSharedModule, RouterModule.forChild(rvRuleRoute)],
  declarations: [RvRuleComponent, RvRuleDetailComponent, RvRuleUpdateComponent, RvRuleDeleteDialogComponent],
  entryComponents: [RvRuleDeleteDialogComponent],
})
export class RulesValidatorRvRuleModule {}
