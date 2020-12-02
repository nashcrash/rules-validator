import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RulesValidatorSharedModule } from 'app/shared/shared.module';
import { RvConverterComponent } from './rv-converter.component';
import { RvConverterDetailComponent } from './rv-converter-detail.component';
import { RvConverterUpdateComponent } from './rv-converter-update.component';
import { RvConverterDeleteDialogComponent } from './rv-converter-delete-dialog.component';
import { rvConverterRoute } from './rv-converter.route';

@NgModule({
  imports: [RulesValidatorSharedModule, RouterModule.forChild(rvConverterRoute)],
  declarations: [RvConverterComponent, RvConverterDetailComponent, RvConverterUpdateComponent, RvConverterDeleteDialogComponent],
  entryComponents: [RvConverterDeleteDialogComponent],
})
export class RulesValidatorRvConverterModule {}
