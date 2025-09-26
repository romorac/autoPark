import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVariables, Variables } from '../variables.model';
import { VariablesService } from '../service/variables.service';

@Injectable({ providedIn: 'root' })
export class VariablesRoutingResolveService implements Resolve<IVariables> {
  constructor(protected service: VariablesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVariables> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((variables: HttpResponse<Variables>) => {
          if (variables.body) {
            return of(variables.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Variables());
  }
}
