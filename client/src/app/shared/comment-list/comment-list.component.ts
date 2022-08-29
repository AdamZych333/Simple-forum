import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Comment } from 'src/app/core';

@Component({
  selector: 'app-comment-list[comments]',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CommentListComponent {
  @Input('comments') comments$!: Observable<Comment[]>;

  constructor() { }

  trackById(index: number, item: Comment){
    return item.id;
  }

  renderTest(){
    console.log('render')
  }
}
