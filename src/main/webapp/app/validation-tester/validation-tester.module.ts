import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RulesValidatorSharedModule } from '../shared/shared.module';

import { VALIDATION_TESTER_ROUTE, ValidationTesterComponent } from './';

@NgModule({
  imports: [RulesValidatorSharedModule, RouterModule.forRoot([VALIDATION_TESTER_ROUTE], { useHash: true })],
  declarations: [ValidationTesterComponent],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class RulesValidatorAppValidationTesterModule {}
