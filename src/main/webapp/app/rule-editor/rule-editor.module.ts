import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RulesValidatorSharedModule } from '../shared/shared.module';

import { RULE_EDITOR_ROUTE, RuleEditorComponent } from './';
import { PageOneComponent } from './page-one/page-one.component';
import { PageTwoComponent } from './page-two/page-two.component';

@NgModule({
  imports: [RulesValidatorSharedModule, RouterModule.forRoot([RULE_EDITOR_ROUTE], { useHash: true })],
  declarations: [RuleEditorComponent, PageOneComponent, PageTwoComponent],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class RulesValidatorAppRuleEditorModule {}
