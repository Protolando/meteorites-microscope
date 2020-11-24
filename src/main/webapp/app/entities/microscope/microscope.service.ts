import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMicroscope } from 'app/shared/model/microscope.model';

type EntityResponseType = HttpResponse<IMicroscope>;
type EntityArrayResponseType = HttpResponse<IMicroscope[]>;

@Injectable({ providedIn: 'root' })
export class MicroscopeService {
  public resourceUrl = SERVER_API_URL + 'api/microscopes';

  constructor(protected http: HttpClient) {}

  create(microscope: IMicroscope): Observable<EntityResponseType> {
    return this.http.post<IMicroscope>(this.resourceUrl, microscope, { observe: 'response' });
  }

  update(microscope: IMicroscope): Observable<EntityResponseType> {
    return this.http.put<IMicroscope>(this.resourceUrl, microscope, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMicroscope>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMicroscope[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
