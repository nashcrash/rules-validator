import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IParameter } from 'app/shared/model/parameter.model';

type EntityResponseType = HttpResponse<IParameter>;
type EntityArrayResponseType = HttpResponse<IParameter[]>;

@Injectable({ providedIn: 'root' })
export class ParameterService {
  public resourceUrl = SERVER_API_URL + 'api/parameters';

  constructor(protected http: HttpClient) {}

  create(parameter: IParameter): Observable<EntityResponseType> {
    return this.http.post<IParameter>(this.resourceUrl, parameter, { observe: 'response' });
  }

  update(parameter: IParameter): Observable<EntityResponseType> {
    return this.http.put<IParameter>(this.resourceUrl, parameter, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParameter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParameter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
