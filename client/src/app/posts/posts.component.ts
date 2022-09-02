import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BehaviorSubject, map, Observable, of, switchMap, tap } from 'rxjs';
import { Comment, Post } from '../core';
import { CommentService } from '../core/services/comment.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PostsComponent {
  post$: Observable<Post> = this.route.data.pipe(
    map(data => data['post'])
  );
  comments$: Observable<Comment[]> = this.route.data.pipe(
    switchMap(data => this.commentService.getPostComments(data['post'].id))
  )
  PAGE_SIZE = 5;
  private pageSubject = new BehaviorSubject<number>(1);
  currentComments$: Observable<Comment[]> = this.pageSubject.pipe(
    switchMap(page => this.comments$.pipe(
      map(comments => comments.slice(0, this.PAGE_SIZE*page))
    ))
  );

  constructor(
    private route: ActivatedRoute,
    private commentService: CommentService,
  ) { }

  trackById(index: number, item: Comment){
    return item.id;
  }

  nextPage(){
    this.pageSubject.next(this.pageSubject.getValue()+1);
  }

}
