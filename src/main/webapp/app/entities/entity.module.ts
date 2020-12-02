import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'rv-rule-group',
        loadChildren: () => import('./rv-rule-group/rv-rule-group.module').then(m => m.RulesValidatorRvRuleGroupModule),
      },
      {
        path: 'rv-rule',
        loadChildren: () => import('./rv-rule/rv-rule.module').then(m => m.RulesValidatorRvRuleModule),
      },
      {
        path: 'rv-operator',
        loadChildren: () => import('./rv-operator/rv-operator.module').then(m => m.RulesValidatorRvOperatorModule),
      },
      {
        path: 'rv-operator-param',
        loadChildren: () => import('./rv-operator-param/rv-operator-param.module').then(m => m.RulesValidatorRvOperatorParamModule),
      },
      {
        path: 'rv-param',
        loadChildren: () => import('./rv-param/rv-param.module').then(m => m.RulesValidatorRvParamModule),
      },
      {
        path: 'rv-converter',
        loadChildren: () => import('./rv-converter/rv-converter.module').then(m => m.RulesValidatorRvConverterModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class RulesValidatorEntityModule {}
