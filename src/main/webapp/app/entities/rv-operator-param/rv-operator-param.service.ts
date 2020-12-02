import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRvOperatorParam } from 'app/shared/model/rv-operator-param.model';

type EntityResponseType = HttpResponse<IRvOperatorParam>;
type EntityArrayResponseType = HttpResponse<IRvOperatorParam[]>;

@Injectable({ providedIn: 'root' })
export class RvOperatorParamService {
  public resourceUrl = SERVER_API_URL + 'api/rv-operator-params';

  constructor(protected http: HttpClient) {}

  create(rvOperatorParam: IRvOperatorParam): Observable<EntityResponseType> {
    return this.http.post<IRvOperatorParam>(this.resourceUrl, rvOperatorParam, { observe: 'response' });
  }

  update(rvOperatorParam: IRvOperatorParam): Observable<EntityResponseType> {
    return this.http.put<IRvOperatorParam>(this.resourceUrl, rvOperatorParam, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRvOperatorParam>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRvOperatorParam[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
