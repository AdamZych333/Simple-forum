import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { Votes } from '../models';
import { ApiService } from './api.service';

@Injectable()
export class VoteService {
  private votesSubject = new BehaviorSubject<Votes>({} as Votes);

  constructor(
    private apiService: ApiService,
  ) { }

  getVotes(postID: number, type: string){
    // this.apiService.get(`/posts/${postID}/follows`,

    // ).subscribe({
    //   next: (e) => this.votesSubject.next(e)
    // });

    // return this.getCurrentFollows();
  }

  votePost(postID: number): void{
    // this.apiService.post(`/posts/${postID}/follows`).pipe(
    //   map(() => {
    //     const newVote = this.votesSubject.getValue();
    //     newVote.voted = true;
    //     newVote.count++;
    //     return newVote;
    //   })
    // )
    // .subscribe({
    //   next: (vote) => this.votesSubject.next(vote),
    // });
  }

  unvotePost(postID: number): void{
    // this.apiService.delete(`/posts/${postID}/follows`).pipe(
    //   map(() => {
    //     const newVote = this.votesSubject.getValue();
    //     newVote.voted = false;
    //     newVote.count--;
    //     return newVote;
    //   })
    // )
    // .subscribe({
    //   next: (follow) => this.votesSubject.next(follow),
    // });
  }

  getCurrentVotes(): Observable<Votes>{
    return this.votesSubject.asObservable();
  }
}
