import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRvConverter } from 'app/shared/model/rv-converter.model';

type EntityResponseType = HttpResponse<IRvConverter>;
type EntityArrayResponseType = HttpResponse<IRvConverter[]>;

@Injectable({ providedIn: 'root' })
export class RvConverterService {
  public resourceUrl = SERVER_API_URL + 'api/rv-converters';

  constructor(protected http: HttpClient) {}

  create(rvConverter: IRvConverter): Observable<EntityResponseType> {
    return this.http.post<IRvConverter>(this.resourceUrl, rvConverter, { observe: 'response' });
  }

  update(rvConverter: IRvConverter): Observable<EntityResponseType> {
    return this.http.put<IRvConverter>(this.resourceUrl, rvConverter, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRvConverter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRvConverter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
