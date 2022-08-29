import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

interface ICommentBody{
  content: string,
  parentID: number,
}

@Injectable()
export class CommentService {

  constructor(
    private apiService: ApiService,
  ) { }

  getPostComments(postID: number): Observable<Comment[]>{
    return this.apiService.get(`/posts/${postID}/comments`);
  }

  addComment(postID: number, body: ICommentBody): Observable<void>{
    return this.apiService.post(`/posts/${postID}/comments`, body);
  }
}
