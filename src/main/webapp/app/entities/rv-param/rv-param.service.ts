import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRvParam } from 'app/shared/model/rv-param.model';

type EntityResponseType = HttpResponse<IRvParam>;
type EntityArrayResponseType = HttpResponse<IRvParam[]>;

@Injectable({ providedIn: 'root' })
export class RvParamService {
  public resourceUrl = SERVER_API_URL + 'api/rv-params';

  constructor(protected http: HttpClient) {}

  create(rvParam: IRvParam): Observable<EntityResponseType> {
    return this.http.post<IRvParam>(this.resourceUrl, rvParam, { observe: 'response' });
  }

  update(rvParam: IRvParam): Observable<EntityResponseType> {
    return this.http.put<IRvParam>(this.resourceUrl, rvParam, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRvParam>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRvParam[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
