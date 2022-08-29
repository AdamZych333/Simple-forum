import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { BehaviorSubject, map, mapTo, Observable, switchMap } from 'rxjs';
import { Comment } from 'src/app/core';

@Component({
  selector: 'app-comment-list[comments]',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CommentListComponent {
  @Input('comments') comments$!: Observable<Comment[]>;
  PAGE_SIZE = 5;
  private pageSubject = new BehaviorSubject<number>(1);
  currentComments$: Observable<Comment[]> = this.pageSubject.pipe(
    switchMap(page => {
      return this.comments$.pipe(
        map(comments => comments.slice(0, this.PAGE_SIZE*(page)))
      )
    })
  );

  constructor() { }

  trackById(index: number, item: Comment){
    return item.id;
  }

  nextPage(){
    this.pageSubject.next(this.pageSubject.getValue()+1);
  }
}
