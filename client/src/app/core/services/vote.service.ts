import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { Votes } from '../models';
import { ApiService } from './api.service';

export interface IVotesParams{
  type: string,
}

@Injectable()
export class VoteService {
  private votesSubject = new BehaviorSubject<Votes>({} as Votes);

  constructor(
    private apiService: ApiService,
  ) { }

  getVotes(postID: number, params: IVotesParams){
    const mappedParams: {[param: string]: string | number} = {...params};

    this.apiService.get(`/posts/${postID}/votes`,
      new HttpParams({fromObject: mappedParams}),
    ).subscribe({
      next: (e) => this.votesSubject.next(e)
    });

    return this.getCurrentVotes();
  }

  votePost(postID: number, params: IVotesParams): void{
    const mappedParams: {[param: string]: string | number} = {...params};

    this.apiService.post(`/posts/${postID}/votes`, mappedParams).pipe(
      map(() => {
        const newVote = this.votesSubject.getValue();
        newVote.voted = true;
        newVote.count++;
        return newVote;
    })
    ).subscribe({
      next: (e) => this.votesSubject.next(e)
    });
  }

  unvotePost(postID: number): void{
    this.apiService.delete(`/posts/${postID}/votes`).pipe(
      map(() => {
        const newVote = this.votesSubject.getValue();
        newVote.voted = false;
        newVote.count--;
        return newVote;
    })
    ).subscribe({
      next: (e) => this.votesSubject.next(e)
    });
  }

  getCurrentVotes(): Observable<Votes>{
    return this.votesSubject.asObservable();
  }
}
