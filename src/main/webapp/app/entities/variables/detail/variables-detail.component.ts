import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVariables } from '../variables.model';

@Component({
  selector: 'jhi-variables-detail',
  templateUrl: './variables-detail.component.html',
})
export class VariablesDetailComponent implements OnInit {
  variables: IVariables | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ variables }) => {
      this.variables = variables;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
