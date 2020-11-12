import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RulesValidatorSharedModule } from 'app/shared/shared.module';
import { ParamComponent } from './param.component';
import { ParamDetailComponent } from './param-detail.component';
import { ParamUpdateComponent } from './param-update.component';
import { ParamDeleteDialogComponent } from './param-delete-dialog.component';
import { paramRoute } from './param.route';

@NgModule({
  imports: [RulesValidatorSharedModule, RouterModule.forChild(paramRoute)],
  declarations: [ParamComponent, ParamDetailComponent, ParamUpdateComponent, ParamDeleteDialogComponent],
  entryComponents: [ParamDeleteDialogComponent],
})
export class RulesValidatorParamModule {}
