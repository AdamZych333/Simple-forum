import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import { Post, Tag } from '../models';
import { ApiService } from './api.service';

export interface IPostQueryParams{
  query?: string,
  sortBy?: string,
  order?: string,
  page?: number,
  pageSize: number,
}

export interface ICreatePostBody{
  title: string,
  content: string,
  tags?: {name: string}[],
}

@Injectable()
export class PostService {

  constructor(
    private apiService: ApiService,
  ) { }

  queryPosts(tag:Tag|null, params: IPostQueryParams): Observable<Post[]> {
    const mappedParams: {[param: string]: string | number} = {...params};

    if(tag){
      return this.apiService.get(`/tags/${tag.id}/posts`,
      new HttpParams({fromObject: mappedParams}),
    );
    }
    return this.apiService.get('/posts', 
      new HttpParams({fromObject: mappedParams}),
    );
  }

  // getByTag(tagId: number, params: IPostQueryParams): Observable<Post[]>{
  //   const mappedParams: {[param: string]: string | number} = {...params};
  //   mappedParams['pageSize'] = this.PAGE_SIZE;

  //   return this.apiService.get(`/tags/${tagId}/posts`,
  //     new HttpParams({fromObject: mappedParams}),
  //   );
  // }

  addPost(body: ICreatePostBody): Observable<void>{
    return this.apiService.post('/posts', body);
  }

}
