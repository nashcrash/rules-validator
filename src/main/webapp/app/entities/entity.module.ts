import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'rule',
        loadChildren: () => import('./rule/rule.module').then(m => m.ValidationRulesRuleModule),
      },
      {
        path: 'operator',
        loadChildren: () => import('./operator/operator.module').then(m => m.ValidationRulesOperatorModule),
      },
      {
        path: 'parameter',
        loadChildren: () => import('./parameter/parameter.module').then(m => m.ValidationRulesParameterModule),
      },
      {
        path: 'converter',
        loadChildren: () => import('./converter/converter.module').then(m => m.ValidationRulesConverterModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ValidationRulesEntityModule {}
