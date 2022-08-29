import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map, Observable, switchMap } from 'rxjs';
import { Post } from '../core';
import { CommentService } from '../core/services/comment.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.sass']
})
export class PostsComponent {
  post$: Observable<Post> = this.route.data.pipe(
    map(data => data['post'])
  );
  comments$: Observable<Comment[]> = this.route.data.pipe(
    switchMap(data => this.commentService.getPostComments(data['post'].id))
  )

  constructor(
    private route: ActivatedRoute,
    private commentService: CommentService,
  ) { }

}
