import { Injectable } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
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
    return this.apiService.get(`/posts/${postID}/comments`).pipe(
      map(comments => {
        let allComments = [] as Comment[];
        for(let comment of comments) {
          allComments = allComments.concat(this.mapChildern(comment));
        };
        return allComments;
      })
    );
  }

  getByUser(userID: number): Observable<Comment[]>{
    return this.apiService.get(`/users/${userID}/comments`);
  }

  addComment(postID: number, body: ICommentBody): Observable<Comment>{
    return this.apiService.post(`/posts/${postID}/comments`, body);
  }

  edit(commentID: number, postID: number, body: ICommentBody){
    return this.apiService.put(`/posts/${postID}/comments/${commentID}`, body);
  }

  delete(commentID: number, postID: number){
    return this.apiService.delete(`/posts/${postID}/comments/${commentID}`);
  }

  private mapChildern(comment: Comment): Comment[]{
    let list = [comment];
    if(comment.children == null || comment.children.length === 0){
      return list;
    }

    for(let child of comment.children){
      list = list.concat(this.mapChildern(child));
    }

    return list;
  }
}
