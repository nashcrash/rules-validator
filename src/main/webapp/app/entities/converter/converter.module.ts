import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ValidationRulesSharedModule } from 'app/shared/shared.module';
import { ConverterComponent } from './converter.component';
import { ConverterDetailComponent } from './converter-detail.component';
import { ConverterUpdateComponent } from './converter-update.component';
import { ConverterDeleteDialogComponent } from './converter-delete-dialog.component';
import { converterRoute } from './converter.route';

@NgModule({
  imports: [ValidationRulesSharedModule, RouterModule.forChild(converterRoute)],
  declarations: [ConverterComponent, ConverterDetailComponent, ConverterUpdateComponent, ConverterDeleteDialogComponent],
  entryComponents: [ConverterDeleteDialogComponent],
})
export class ValidationRulesConverterModule {}
