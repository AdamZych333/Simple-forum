import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { Post } from '../models';
import { ApiService } from './api.service';
import { UserService } from './user.service';

export interface IPostQueryParams{
  query?: string,
  sortBy?: string,
  order?: string,
  page?: number,
}

export interface ICreatePostBody{
  title: string,
  content: string,
  tags?: {name: string}[],
}

@Injectable()
export class PostService {
  private PAGE_SIZE = 7;


  constructor(
    private apiService: ApiService,
  ) { }

  queryPosts(params: IPostQueryParams): Observable<Post[]> {
    const mappedParams: {[param: string]: string | number} = {...params};
    mappedParams['pageSize'] = this.PAGE_SIZE;

    return this.apiService.get('/posts', 
      new HttpParams({fromObject: mappedParams}),
    );
  }

  getByTag(tagId: number): Observable<Post[]>{
    return this.apiService.get(`/tags/${tagId}/posts`);
  }

  addPost(body: ICreatePostBody): Observable<void>{
    return this.apiService.post('/posts', body);
  }

  followPost(postID: number): Observable<void>{
    return this.apiService.post(`/posts/${postID}/follows`);
  }

  unfollowPost(postID: number): Observable<void>{
    return this.apiService.delete(`/posts/${postID}/follows`);
  }
}
