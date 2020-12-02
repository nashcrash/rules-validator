import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRvRule } from 'app/shared/model/rv-rule.model';

type EntityResponseType = HttpResponse<IRvRule>;
type EntityArrayResponseType = HttpResponse<IRvRule[]>;

@Injectable({ providedIn: 'root' })
export class RvRuleService {
  public resourceUrl = SERVER_API_URL + 'api/rv-rules';

  constructor(protected http: HttpClient) {}

  create(rvRule: IRvRule): Observable<EntityResponseType> {
    return this.http.post<IRvRule>(this.resourceUrl, rvRule, { observe: 'response' });
  }

  update(rvRule: IRvRule): Observable<EntityResponseType> {
    return this.http.put<IRvRule>(this.resourceUrl, rvRule, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRvRule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRvRule[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
