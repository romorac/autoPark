import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVariables } from '../variables.model';
import { VariablesService } from '../service/variables.service';

@Component({
  templateUrl: './variables-delete-dialog.component.html',
})
export class VariablesDeleteDialogComponent {
  variables?: IVariables;

  constructor(protected variablesService: VariablesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.variablesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
