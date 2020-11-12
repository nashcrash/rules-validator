import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RulesValidatorSharedModule } from 'app/shared/shared.module';
import { RvOperatorComponent } from './rv-operator.component';
import { RvOperatorDetailComponent } from './rv-operator-detail.component';
import { RvOperatorUpdateComponent } from './rv-operator-update.component';
import { RvOperatorDeleteDialogComponent } from './rv-operator-delete-dialog.component';
import { rvOperatorRoute } from './rv-operator.route';

@NgModule({
  imports: [RulesValidatorSharedModule, RouterModule.forChild(rvOperatorRoute)],
  declarations: [RvOperatorComponent, RvOperatorDetailComponent, RvOperatorUpdateComponent, RvOperatorDeleteDialogComponent],
  entryComponents: [RvOperatorDeleteDialogComponent],
})
export class RulesValidatorRvOperatorModule {}
