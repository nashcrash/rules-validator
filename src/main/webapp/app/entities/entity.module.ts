import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'rule',
        loadChildren: () => import('./rule/rule.module').then(m => m.RulesValidatorRuleModule),
      },
      {
        path: 'operator',
        loadChildren: () => import('./operator/operator.module').then(m => m.RulesValidatorOperatorModule),
      },
      {
        path: 'param',
        loadChildren: () => import('./param/param.module').then(m => m.RulesValidatorParamModule),
      },
      {
        path: 'converter',
        loadChildren: () => import('./converter/converter.module').then(m => m.RulesValidatorConverterModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class RulesValidatorEntityModule {}
