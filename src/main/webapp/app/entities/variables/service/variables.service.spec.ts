import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { EstadoEnum } from 'app/entities/enumerations/estado-enum.model';
import { IVariables, Variables } from '../variables.model';

import { VariablesService } from './variables.service';

describe('Service Tests', () => {
  describe('Variables Service', () => {
    let service: VariablesService;
    let httpMock: HttpTestingController;
    let elemDefault: IVariables;
    let expectedResult: IVariables | IVariables[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VariablesService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nombre: 'AAAAAAA',
        valor: 'AAAAAAA',
        estado: EstadoEnum.ON,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Variables', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Variables()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Variables', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nombre: 'BBBBBB',
            valor: 'BBBBBB',
            estado: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Variables', () => {
        const patchObject = Object.assign(
          {
            nombre: 'BBBBBB',
            valor: 'BBBBBB',
          },
          new Variables()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Variables', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nombre: 'BBBBBB',
            valor: 'BBBBBB',
            estado: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Variables', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVariablesToCollectionIfMissing', () => {
        it('should add a Variables to an empty array', () => {
          const variables: IVariables = { id: 123 };
          expectedResult = service.addVariablesToCollectionIfMissing([], variables);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(variables);
        });

        it('should not add a Variables to an array that contains it', () => {
          const variables: IVariables = { id: 123 };
          const variablesCollection: IVariables[] = [
            {
              ...variables,
            },
            { id: 456 },
          ];
          expectedResult = service.addVariablesToCollectionIfMissing(variablesCollection, variables);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Variables to an array that doesn't contain it", () => {
          const variables: IVariables = { id: 123 };
          const variablesCollection: IVariables[] = [{ id: 456 }];
          expectedResult = service.addVariablesToCollectionIfMissing(variablesCollection, variables);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(variables);
        });

        it('should add only unique Variables to an array', () => {
          const variablesArray: IVariables[] = [{ id: 123 }, { id: 456 }, { id: 46742 }];
          const variablesCollection: IVariables[] = [{ id: 123 }];
          expectedResult = service.addVariablesToCollectionIfMissing(variablesCollection, ...variablesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const variables: IVariables = { id: 123 };
          const variables2: IVariables = { id: 456 };
          expectedResult = service.addVariablesToCollectionIfMissing([], variables, variables2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(variables);
          expect(expectedResult).toContain(variables2);
        });

        it('should accept null and undefined values', () => {
          const variables: IVariables = { id: 123 };
          expectedResult = service.addVariablesToCollectionIfMissing([], null, variables, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(variables);
        });

        it('should return initial array if no Variables is added', () => {
          const variablesCollection: IVariables[] = [{ id: 123 }];
          expectedResult = service.addVariablesToCollectionIfMissing(variablesCollection, undefined, null);
          expect(expectedResult).toEqual(variablesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
