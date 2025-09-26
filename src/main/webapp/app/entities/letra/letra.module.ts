import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LetraRoutingModule } from './route/letra-routing.module';
import { GenLetraPaso1Component } from './gen-letra-paso1/gen-letra-paso1.component';

@NgModule({
  imports: [SharedModule, LetraRoutingModule],
  declarations: [GenLetraPaso1Component],
})
export class LetraModule {}
