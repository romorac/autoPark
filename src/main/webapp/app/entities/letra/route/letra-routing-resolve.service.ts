import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of } from 'rxjs';

import { ILetra, Letra } from '../letra.model';
import { LetraService } from '../service/letra.service';

@Injectable({ providedIn: 'root' })
export class LetraRoutingResolveService implements Resolve<ILetra> {
  constructor(protected service: LetraService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILetra> | Observable<never> {
    return of(new Letra());
  }
}
