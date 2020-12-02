import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RulesValidatorSharedModule } from 'app/shared/shared.module';
import { RvOperatorParamComponent } from './rv-operator-param.component';
import { RvOperatorParamDetailComponent } from './rv-operator-param-detail.component';
import { RvOperatorParamUpdateComponent } from './rv-operator-param-update.component';
import { RvOperatorParamDeleteDialogComponent } from './rv-operator-param-delete-dialog.component';
import { rvOperatorParamRoute } from './rv-operator-param.route';

@NgModule({
  imports: [RulesValidatorSharedModule, RouterModule.forChild(rvOperatorParamRoute)],
  declarations: [
    RvOperatorParamComponent,
    RvOperatorParamDetailComponent,
    RvOperatorParamUpdateComponent,
    RvOperatorParamDeleteDialogComponent,
  ],
  entryComponents: [RvOperatorParamDeleteDialogComponent],
})
export class RulesValidatorRvOperatorParamModule {}
