import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VariablesComponent } from './list/variables.component';
import { VariablesDetailComponent } from './detail/variables-detail.component';
import { VariablesUpdateComponent } from './update/variables-update.component';
import { VariablesDeleteDialogComponent } from './delete/variables-delete-dialog.component';
import { VariablesRoutingModule } from './route/variables-routing.module';

@NgModule({
  imports: [SharedModule, VariablesRoutingModule],
  declarations: [VariablesComponent, VariablesDetailComponent, VariablesUpdateComponent, VariablesDeleteDialogComponent],
  entryComponents: [VariablesDeleteDialogComponent],
})
export class VariablesModule {}
