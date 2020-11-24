import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRock } from 'app/shared/model/rock.model';

type EntityResponseType = HttpResponse<IRock>;
type EntityArrayResponseType = HttpResponse<IRock[]>;

@Injectable({ providedIn: 'root' })
export class RockService {
  public resourceUrl = SERVER_API_URL + 'api/rocks';

  constructor(protected http: HttpClient) {}

  create(rock: IRock): Observable<EntityResponseType> {
    return this.http.post<IRock>(this.resourceUrl, rock, { observe: 'response' });
  }

  update(rock: IRock): Observable<EntityResponseType> {
    return this.http.put<IRock>(this.resourceUrl, rock, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRock[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
