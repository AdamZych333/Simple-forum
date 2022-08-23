import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../models';
import { ApiService } from './api.service';

interface IPostQueryParams{
  query?: string,
  sortBy?: string,
  order?: string,
  page?: number,
  pageSize?: number,
}

@Injectable()
export class PostService {

  constructor(
    private apiService: ApiService,
  ) { }

  queryPosts(params: IPostQueryParams): Observable<Post[]> {
    const mappedParams: {[param: string]: string | number} = {...params};

    return this.apiService.get(
      '/posts', 
      new HttpParams({fromObject: mappedParams}),
    );
  }
}
