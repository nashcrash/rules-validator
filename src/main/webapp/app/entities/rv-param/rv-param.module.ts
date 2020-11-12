import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RulesValidatorSharedModule } from 'app/shared/shared.module';
import { RvParamComponent } from './rv-param.component';
import { RvParamDetailComponent } from './rv-param-detail.component';
import { RvParamUpdateComponent } from './rv-param-update.component';
import { RvParamDeleteDialogComponent } from './rv-param-delete-dialog.component';
import { rvParamRoute } from './rv-param.route';

@NgModule({
  imports: [RulesValidatorSharedModule, RouterModule.forChild(rvParamRoute)],
  declarations: [RvParamComponent, RvParamDetailComponent, RvParamUpdateComponent, RvParamDeleteDialogComponent],
  entryComponents: [RvParamDeleteDialogComponent],
})
export class RulesValidatorRvParamModule {}
