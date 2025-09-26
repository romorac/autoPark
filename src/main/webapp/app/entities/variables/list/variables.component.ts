import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVariables } from '../variables.model';
import { VariablesService } from '../service/variables.service';
import { VariablesDeleteDialogComponent } from '../delete/variables-delete-dialog.component';

@Component({
  selector: 'jhi-variables',
  templateUrl: './variables.component.html',
})
export class VariablesComponent implements OnInit {
  variables?: IVariables[];
  isLoading = false;

  constructor(protected variablesService: VariablesService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.variablesService.query().subscribe(
      (res: HttpResponse<IVariables[]>) => {
        this.isLoading = false;
        this.variables = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IVariables): number {
    return item.id!;
  }

  delete(variables: IVariables): void {
    const modalRef = this.modalService.open(VariablesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.variables = variables;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
