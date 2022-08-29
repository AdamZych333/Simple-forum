import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Comment } from '../models';
import { ApiService } from './api.service';

interface ICommentBody{
  content: string,
  parentID: number | null,
}

@Injectable()
export class CommentService {

  constructor(
    private apiService: ApiService,
  ) { }

  getPostComments(postID: number): Observable<Comment[]>{
    return this.apiService.get(`/posts/${postID}/comments`);
  }

  addComment(postID: number, body: ICommentBody): Observable<Comment>{
    return this.apiService.post(`/posts/${postID}/comments`, body);
  }
}
