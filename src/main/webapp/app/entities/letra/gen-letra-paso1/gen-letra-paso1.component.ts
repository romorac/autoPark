import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';
import { ILetra } from '../letra.model';
import { LetraService } from '../service/letra.service';

import { DetalleLetraPago, ILetraServicio, LetraServicio } from '../letraServicio.model';

@Component({
  selector: 'jhi-gen-letra-paso1',
  templateUrl: './gen-letra-paso1.component.html',
  styleUrls: ['./gen-letra-paso1.component.scss'],
})
export class GenLetraPaso1Component implements OnInit {
  @ViewChild('content') content: any;
  @ViewChild('contentEditRow') contentEditRow: any;
  ngbPaginationPage = 1;
  variables?: string[];

  isSaving = false;
  columnas: string[] = ['codigo', 'descripcion', 'precio', 'borrar'];

  datos: DetalleLetraPago[] = [];

  editForm: FormGroup = this.fb.group({});
  newRowForm: FormGroup = this.fb.group({});
  editRowForm: FormGroup = this.fb.group({});

  constructor(
    protected letraService: LetraService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected fb: FormBuilder,
    private modalService: NgbModal,
    private modalServiceEditRow: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ letra }) => {
      this.updateForm(letra);
    });

    this.editForm = this.fb.group({
      nombrePagador: [null, [Validators.required, Validators.maxLength(100)]],
      ciudadGiro: [null, [Validators.required, Validators.maxLength(100)]],
      fechaGiro: [null, [Validators.required]],
      montoNumerico: [null, [Validators.required, Validators.min(0)]],
      nombreBeneficiario: [null, [Validators.required, Validators.maxLength(100)]],
      domicilioBeneficiario: [null, [Validators.required, Validators.maxLength(100)]],
      ciudadBeneficiario: [null, [Validators.required, Validators.maxLength(100)]],
      comunaBeneficiario: [null, [Validators.required, Validators.maxLength(100)]],
      rutBeneficiario: [null, [Validators.required, Validators.maxLength(100)]],
      detalleLetra: [null, [Validators.required]],
    });

    const today = new Date();
    this.newRowForm = this.fb.group({
      numeroLetra: [this.datos.length + 1, Validators.required],
      fechaVencimiento: [today, Validators.required],
      monto: ['0', [Validators.required, Validators.min(0)]],
      numCuotas: ['1', [Validators.required, Validators.min(1), Validators.max(99)]],
    });

    this.editRowForm = this.fb.group({
      numeroLetra: [],
      fechaVencimiento: [],
      monto: ['0', [Validators.required, Validators.min(0)]],
    });
  }

  previousState(): void {
    window.history.back();
  }

  genLetrasPdf(): void {
    this.isSaving = true;
    const formulario = this.createFromForm();
    this.letraService.generacionLetrasPago(formulario);
  }

  deleteRow(row: any): void {
    // Find the index of the row to delete
    const index = this.datos.indexOf(row);
    if (index > -1) {
      // Remove the row from the array
      this.datos.splice(index, 1);
    }
  }

  editRow(row: any): void {
    // Pre-fill the form with the row data
    this.editRowForm.patchValue({
      numeroLetra: row.numeroLetra,
      fechaVencimiento: row.fechaVencimiento,
      monto: row.monto,
    });
    this.datos = this.datos.filter(item => item.numeroLetra !== this.newRowForm.get('numeroLetra')?.value);
    this.datos = this.datos.filter(item => item.numeroLetra !== this.editRowForm.get('numeroLetra')?.value);

    // Open the modal
    this.openEditModal();
  }
  openModal(): void {
    this.modalService.open(this.content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      result => {
        const mes = new Date(this.newRowForm.get('fechaVencimiento')?.value).getMonth();
        const anio = new Date(this.newRowForm.get('fechaVencimiento')?.value).getFullYear();
        // eslint-disable-next-line no-console
        console.log(this.newRowForm.get('fechaVencimiento')?.value);

        if (this.newRowForm.valid) {
          for (let index = 0; index < this.newRowForm.get('numCuotas')!.value; index++) {
            let fechaVencimiento = this.newRowForm.get('fechaVencimiento')?.value;
            // eslint-disable-next-line no-console
            console.log(
              'indice:%s   mes:%s  mod:%s  año:%s',
              index + 1,
              mes + index,
              ((mes + index) % 12) + 1,
              Math.floor((mes + index) / 12)
            ); // Example console statement

            //            fechaVencimiento = new Date(this.newRowForm.get('fechaVencimiento')?.value).setMonth(

            fechaVencimiento = new Date(fechaVencimiento).setMonth((mes + index) % 12);
            fechaVencimiento = new Date(fechaVencimiento).setFullYear(anio + Math.floor((mes + index) / 12));

            this.newRowForm.patchValue({ numeroLetra: index + 1, fechaVencimiento }); // Correctly patch the value
            this.datos.push(this.newRowForm.value);
          }
          this.newRowForm.reset();
        }
      },
      reason => {
        // Modal dismissed
      }
    );
  }
  openEditModal(): void {
    this.modalServiceEditRow.open(this.contentEditRow, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      result => {
        if (this.editRowForm.valid) {
          this.datos.push(this.editRowForm.value);
          this.datos.sort((a, b) => Number(a.numeroLetra!) - Number(b.numeroLetra!));
          this.editRowForm.reset();
        }
      },
      reason => {
        // Modal dismissed
      }
    );
  }

  protected createFromForm(): ILetraServicio {
    return {
      ...new LetraServicio(),
      nombrePagador: this.editForm.get(['nombrePagador'])!.value.toUpperCase(),
      ciudadGiro: this.editForm.get(['ciudadGiro'])!.value.toUpperCase(),
      fechaGiro: this.editForm.get(['fechaGiro'])!.value,
      montoNumerico: this.editForm.get(['montoNumerico'])!.value,
      nombreBeneficiario: this.editForm.get(['nombreBeneficiario'])!.value.toUpperCase(),
      domicilioBeneficiario: this.editForm.get(['domicilioBeneficiario'])!.value,
      ciudadBeneficiario: this.editForm.get(['ciudadBeneficiario'])!.value.toUpperCase(),
      comunaBeneficiario: this.editForm.get(['comunaBeneficiario'])!.value.toUpperCase(),
      rutBeneficiario: this.editForm.get(['rutBeneficiario'])!.value,
      lstDetalleLetra: this.datos,
    };
  }

  protected updateForm(letra: ILetra): void {
    this.editForm.patchValue({
      nombre: letra.nombre,
      detalleLetra: this.datos,
    });
  }
}
