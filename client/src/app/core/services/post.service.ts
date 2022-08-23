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

@Injectable()
export class PostService {
  private PAGE_SIZE = 7;


  constructor(
    private apiService: ApiService,
    private userService: UserService,
  ) { }

  queryPosts(params: IPostQueryParams): Observable<Post[]> {
    const mappedParams: {[param: string]: string | number} = {...params};
    mappedParams['pageSize'] = this.PAGE_SIZE;

    return this.apiService.get(
      '/posts', 
      new HttpParams({fromObject: mappedParams}),
    ).pipe(
      // Map userID to username
      map((posts) => {
        return posts.map((post: any) => {
          this.userService.getUser(post.userID).subscribe({
            next: (user) => post.username = user.name,
            error: () => console.log(`error fetching username for post ${post.id}`)
          })

          return post;
        })
      })
    );
  }
}
