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
    ).pipe(
      map((posts) => posts.map((post: any) => this.mapPost(post))),
    );
  }

  getByTag(tagId: number){
    return this.apiService.get(`/tags/${tagId}/posts`).pipe(
      map((posts) => posts.map((post: any) => this.mapPost(post))),
    );
  }

  addPost(body: ICreatePostBody): Observable<void>{
    
    return this.apiService.post('/posts', body);
  }

  private mapPost(post: any){    
    // Map votes to count
    post.vote = {
      up: post.votes.find((v: {type: string, count: number}) => v.type == "UP").count,
      down: post.votes.find((v: {type: string, count: number}) => v.type == "DOWN").count,
    }

    return post;
  }
}
