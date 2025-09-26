import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'variables',
        data: { pageTitle: 'Variables' },
        loadChildren: () => import('./variables/variables.module').then(m => m.VariablesModule),
      },

      {
        path: 'letra',
        data: { pageTitle: 'Pago Letras' },
        loadChildren: () => import('./letra/letra.module').then(m => m.LetraModule),
      },

      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
