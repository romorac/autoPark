import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ILetraServicio } from '../letraServicio.model';
import { IFilePdf } from 'app/entities/utilModels/filePdf.model';

export type EntityResponseFileType = HttpResponse<IFilePdf>;

@Injectable({
  providedIn: 'root',
})
export class LetraService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/letras');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  generacionLetrasPago(letra: ILetraServicio): void {
    const copy = this.convertDateFromClient(letra);
    const promise = this.http
      .post<IFilePdf>(`${this.resourceUrl}/generaPdf`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseFileType) => this.convertDateFileFromServer(res)))
      .toPromise();
    promise
      .then(data => {
        const otro = <IFilePdf>data.body;
        const linkSource = `${String('data:application/pdf;base64,')}${String(otro.result.base64)}`;
        const downloadLink = document.createElement('a');
        const fileName = otro.result.nombreArchivo;
        downloadLink.href = linkSource;
        downloadLink.download = fileName;
        downloadLink.click();
      })
      .catch(error => {
        console.error('Promise rejected with ' + JSON.stringify(error));
      });
  }

  protected convertDateFromClient(letra: ILetraServicio): ILetraServicio {
    return Object.assign({}, letra, {
      //      fechaRecepcion: letra.fechaRecepcion?.isValid() ? letra.fechaRecepcion.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFileFromServer(res: EntityResponseFileType): EntityResponseFileType {
    console.warn('lerta.service');
    return res;
  }
}
