import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tag } from '../models';
import { ApiService } from './api.service';

export interface ITagQueryParams{
  page?: number,
  pageSize?: number,
}

@Injectable()
export class TagService {
  
  constructor(
    private apiService: ApiService,
  ) { }

  getPopularTags(params: ITagQueryParams): Observable<Tag[]>{
    const mappedParams: {[param: string]: string | number} = {...params};

    return this.apiService.get('/tags',
      new HttpParams({fromObject: mappedParams}),
    );
  }
}
