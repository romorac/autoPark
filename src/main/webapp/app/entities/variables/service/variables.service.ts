import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVariables, getVariablesIdentifier } from '../variables.model';

export type EntityResponseType = HttpResponse<IVariables>;
export type EntityArrayResponseType = HttpResponse<IVariables[]>;

@Injectable({ providedIn: 'root' })
export class VariablesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/variables');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(variables: IVariables): Observable<EntityResponseType> {
    return this.http.post<IVariables>(this.resourceUrl, variables, { observe: 'response' });
  }

  update(variables: IVariables): Observable<EntityResponseType> {
    return this.http.put<IVariables>(`${this.resourceUrl}/${getVariablesIdentifier(variables) as number}`, variables, {
      observe: 'response',
    });
  }

  partialUpdate(variables: IVariables): Observable<EntityResponseType> {
    return this.http.patch<IVariables>(`${this.resourceUrl}/${getVariablesIdentifier(variables) as number}`, variables, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVariables>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVariables[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVariablesToCollectionIfMissing(
    variablesCollection: IVariables[],
    ...variablesToCheck: (IVariables | null | undefined)[]
  ): IVariables[] {
    const variables: IVariables[] = variablesToCheck.filter(isPresent);
    if (variables.length > 0) {
      const variablesCollectionIdentifiers = variablesCollection.map(variablesItem => getVariablesIdentifier(variablesItem)!);
      const variablesToAdd = variables.filter(variablesItem => {
        const variablesIdentifier = getVariablesIdentifier(variablesItem);
        if (variablesIdentifier == null || variablesCollectionIdentifiers.includes(variablesIdentifier)) {
          return false;
        }
        variablesCollectionIdentifiers.push(variablesIdentifier);
        return true;
      });
      return [...variablesToAdd, ...variablesCollection];
    }
    return variablesCollection;
  }
}
