import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GenLetraPaso1Component } from '../gen-letra-paso1/gen-letra-paso1.component';
import { LetraRoutingResolveService } from '../route/letra-routing-resolve.service';
const LetraRoute: Routes = [
  {
    path: '',
    component: GenLetraPaso1Component,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },

  {
    path: 'new',
    component: GenLetraPaso1Component,
    resolve: {
      venta: LetraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(LetraRoute)],
  exports: [RouterModule],
})
export class LetraRoutingModule {}
