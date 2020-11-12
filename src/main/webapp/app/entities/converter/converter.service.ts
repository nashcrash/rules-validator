import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConverter } from 'app/shared/model/converter.model';

type EntityResponseType = HttpResponse<IConverter>;
type EntityArrayResponseType = HttpResponse<IConverter[]>;

@Injectable({ providedIn: 'root' })
export class ConverterService {
  public resourceUrl = SERVER_API_URL + 'api/converters';

  constructor(protected http: HttpClient) {}

  create(converter: IConverter): Observable<EntityResponseType> {
    return this.http.post<IConverter>(this.resourceUrl, converter, { observe: 'response' });
  }

  update(converter: IConverter): Observable<EntityResponseType> {
    return this.http.put<IConverter>(this.resourceUrl, converter, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConverter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConverter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
