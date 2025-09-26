import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IVariables, Variables } from '../variables.model';
import { VariablesService } from '../service/variables.service';

@Component({
  selector: 'jhi-variables-update',
  templateUrl: './variables-update.component.html',
})
export class VariablesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(30)]],
    valor: [null, [Validators.required, Validators.maxLength(30)]],
    estado: [null, [Validators.required]],
  });

  constructor(protected variablesService: VariablesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ variables }) => {
      this.updateForm(variables);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const variables = this.createFromForm();
    if (variables.id !== undefined) {
      this.subscribeToSaveResponse(this.variablesService.update(variables));
    } else {
      this.subscribeToSaveResponse(this.variablesService.create(variables));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVariables>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(variables: IVariables): void {
    this.editForm.patchValue({
      id: variables.id,
      nombre: variables.nombre,
      valor: variables.valor,
      estado: variables.estado,
    });
  }

  protected createFromForm(): IVariables {
    return {
      ...new Variables(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      estado: this.editForm.get(['estado'])!.value,
    };
  }
}
