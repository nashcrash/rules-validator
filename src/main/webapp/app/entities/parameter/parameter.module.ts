import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RulesValidatorSharedModule } from 'app/shared/shared.module';
import { ParameterComponent } from './parameter.component';
import { ParameterDetailComponent } from './parameter-detail.component';
import { ParameterUpdateComponent } from './parameter-update.component';
import { ParameterDeleteDialogComponent } from './parameter-delete-dialog.component';
import { parameterRoute } from './parameter.route';

@NgModule({
  imports: [RulesValidatorSharedModule, RouterModule.forChild(parameterRoute)],
  declarations: [ParameterComponent, ParameterDetailComponent, ParameterUpdateComponent, ParameterDeleteDialogComponent],
  entryComponents: [ParameterDeleteDialogComponent],
})
export class RulesValidatorParameterModule {}
