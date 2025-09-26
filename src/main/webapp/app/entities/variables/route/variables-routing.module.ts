import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VariablesComponent } from '../list/variables.component';
import { VariablesDetailComponent } from '../detail/variables-detail.component';
import { VariablesUpdateComponent } from '../update/variables-update.component';
import { VariablesRoutingResolveService } from './variables-routing-resolve.service';

const variablesRoute: Routes = [
  {
    path: '',
    component: VariablesComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VariablesDetailComponent,
    resolve: {
      variables: VariablesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VariablesUpdateComponent,
    resolve: {
      variables: VariablesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VariablesUpdateComponent,
    resolve: {
      variables: VariablesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(variablesRoute)],
  exports: [RouterModule],
})
export class VariablesRoutingModule {}
