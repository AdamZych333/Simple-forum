import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { Follow } from '../models';
import { ApiService } from './api.service';

@Injectable()
export class FollowService {
  private followsSubject = new BehaviorSubject<Follow>({} as Follow);

  constructor(
    private apiService: ApiService,
  ) { }

  getFollows(postID: number): Observable<Follow>{
    this.apiService.get(`/posts/${postID}/follows`).subscribe({
      next: (e) => this.followsSubject.next(e)
    });

    return this.getCurrentFollows();
  }

  followPost(postID: number): void{
    this.apiService.post(`/posts/${postID}/follows`).pipe(
      map(() => {
        const newFollow = this.followsSubject.getValue();
        newFollow.followed = true;
        newFollow.count++;
        return newFollow;
      })
    )
    .subscribe({
      next: (follow) => this.followsSubject.next(follow),
    });
  }

  unfollowPost(postID: number): void{
    this.apiService.delete(`/posts/${postID}/follows`).pipe(
      map(() => {
        const newFollow = this.followsSubject.getValue();
        newFollow.followed = false;
        newFollow.count--;
        return newFollow;
      })
    )
    .subscribe({
      next: (follow) => this.followsSubject.next(follow),
    });
  }

  getCurrentFollows(): Observable<Follow>{
    return this.followsSubject.asObservable();
  }
}
