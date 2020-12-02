import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRvRuleGroup } from 'app/shared/model/rv-rule-group.model';

type EntityResponseType = HttpResponse<IRvRuleGroup>;
type EntityArrayResponseType = HttpResponse<IRvRuleGroup[]>;

@Injectable({ providedIn: 'root' })
export class RvRuleGroupService {
  public resourceUrl = SERVER_API_URL + 'api/rv-rule-groups';

  constructor(protected http: HttpClient) {}

  create(rvRuleGroup: IRvRuleGroup): Observable<EntityResponseType> {
    return this.http.post<IRvRuleGroup>(this.resourceUrl, rvRuleGroup, { observe: 'response' });
  }

  update(rvRuleGroup: IRvRuleGroup): Observable<EntityResponseType> {
    return this.http.put<IRvRuleGroup>(this.resourceUrl, rvRuleGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRvRuleGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRvRuleGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
