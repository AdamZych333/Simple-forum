import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tag } from '../models';
import { ApiService } from './api.service';

export interface ITagQueryParams{
  page?: number,
}

@Injectable({
  providedIn: 'root'
})
export class TagService {
  private PAGE_SIZE = 5;

  constructor(
    private apiService: ApiService,
  ) { }

  getPopularTags(params: ITagQueryParams): Observable<Tag[]>{
    const mappedParams: {[param: string]: string | number} = {...params};
    mappedParams['pageSize'] = this.PAGE_SIZE;

    return this.apiService.get('/tags',
      new HttpParams({fromObject: mappedParams}),
    );
  }
}
