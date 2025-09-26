jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVariables, Variables } from '../variables.model';
import { VariablesService } from '../service/variables.service';

import { VariablesRoutingResolveService } from './variables-routing-resolve.service';

describe('Service Tests', () => {
  describe('Variables routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VariablesRoutingResolveService;
    let service: VariablesService;
    let resultVariables: IVariables | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VariablesRoutingResolveService);
      service = TestBed.inject(VariablesService);
      resultVariables = undefined;
    });

    describe('resolve', () => {
      it('should return IVariables returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVariables = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVariables).toEqual({ id: 123 });
      });

      it('should return new IVariables if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVariables = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVariables).toEqual(new Variables());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Variables })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVariables = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVariables).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
