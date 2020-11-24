import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMicroscopePicture } from 'app/shared/model/microscope-picture.model';

type EntityResponseType = HttpResponse<IMicroscopePicture>;
type EntityArrayResponseType = HttpResponse<IMicroscopePicture[]>;

@Injectable({ providedIn: 'root' })
export class MicroscopePictureService {
  public resourceUrl = SERVER_API_URL + 'api/microscope-pictures';

  constructor(protected http: HttpClient) {}

  create(microscopePicture: IMicroscopePicture): Observable<EntityResponseType> {
    return this.http.post<IMicroscopePicture>(this.resourceUrl, microscopePicture, { observe: 'response' });
  }

  update(microscopePicture: IMicroscopePicture): Observable<EntityResponseType> {
    return this.http.put<IMicroscopePicture>(this.resourceUrl, microscopePicture, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMicroscopePicture>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMicroscopePicture[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
