import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { Votes, VoteTypes } from '../models';
import { ApiService } from './api.service';

export interface IVotesParams{
  type: string,
}

@Injectable()
export class VoteService {
  private votesUpSubject = new BehaviorSubject<Votes>({} as Votes);
  private votesDownSubject = new BehaviorSubject<Votes>({} as Votes);

  constructor(
    private apiService: ApiService,
  ) { }

  getVotes(postID: number, params: IVotesParams){
    const mappedParams: {[param: string]: string | number} = {...params};

    this.apiService.get(`/posts/${postID}/votes`,
      new HttpParams({fromObject: mappedParams}),
    ).subscribe({
      next: (e) => {
        if(params.type === VoteTypes.UP) this.votesUpSubject.next(e);
        else this.votesDownSubject.next(e);
      }
    });

    if(params.type === VoteTypes.UP) return this.getCurrentVotesUp();
    else return this.getCurrentVotesDown();
  }

  votePost(postID: number, params: IVotesParams): void{
    const mappedParams: {[param: string]: string | number} = {...params};

    this.apiService.post(`/posts/${postID}/votes`, mappedParams).subscribe({
      next: () => {
        if(params.type === VoteTypes.UP) {
          this.vote(this.votesUpSubject, this.votesDownSubject);
        }
        else {
          this.vote(this.votesDownSubject, this.votesUpSubject);
        }
      }
    });
  }

  unvotePost(postID: number): void{
    this.apiService.delete(`/posts/${postID}/votes`).subscribe({
      next: () => {
        if(this.votesUpSubject.getValue().voted) {
          this.votesUpSubject.next({
            ...this.votesUpSubject.getValue(), 
            count: this.votesUpSubject.getValue().count-1,
            voted: false,
          });
        }
        else {
          this.votesDownSubject.next({
            ...this.votesDownSubject.getValue(), 
            count: this.votesDownSubject.getValue().count-1,
            voted: false,
          });
        }
      }
    });
  }

  getCurrentVotesUp(): Observable<Votes>{
    return this.votesUpSubject.asObservable();
  }

  getCurrentVotesDown(): Observable<Votes>{
    return this.votesDownSubject.asObservable();
  }

  private vote(triggered: BehaviorSubject<Votes>, other: BehaviorSubject<Votes>){
    // Increase count of the triggered one
    triggered.next({
      ...triggered.getValue(), 
      count: triggered.getValue().count+1,
      voted: true,
    });

    // Decrease count of the other one
    if(other.getValue().voted){
      other.next({
        ...other.getValue(), 
        count: other.getValue().count-1,
        voted: false,
      });
    }

  }

  private voteDown(){

  }
}
