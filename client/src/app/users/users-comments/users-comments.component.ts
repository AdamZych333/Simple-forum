import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map, switchMap, tap, BehaviorSubject, of } from 'rxjs';
import { Comment, CommentService, User } from 'src/app/core';

@Component({
  selector: 'app-users-comments',
  templateUrl: './users-comments.component.html',
  styleUrls: ['./users-comments.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UsersCommentsComponent {
  user$: Observable<User> | undefined = this.route.parent?.data.pipe(
    map(data => data[0]),
  );

  createdComments$ = this.user$?.pipe(
    switchMap(user => this.commentService.getByUser(user.id)),
  )

  PAGE_SIZE = 5;
  private pageSubject = new BehaviorSubject<number>(1);
  currentComments$: Observable<Comment[]> = this.pageSubject.pipe(
    switchMap(page => {
      if(!this.createdComments$) return of([]);
      return this.createdComments$.pipe(
        map(comments => comments.slice(0, this.PAGE_SIZE*page))
      )
    })
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
