import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { ValidationRequest, ValidationResult } from 'app/shared/model/validation-tester.dto';

type EntityResponseType = HttpResponse<ValidationResult>;

@Injectable({
  providedIn: 'root',
})
export class ValidationTesterService {
  public resourceUrl = SERVER_API_URL + 'api/validate/';

  constructor(protected http: HttpClient) {}

  validate(request: ValidationRequest): Observable<EntityResponseType> {
    return this.http.put<ValidationResult>(this.resourceUrl + request.type, request, { observe: 'response' });
  }
}
