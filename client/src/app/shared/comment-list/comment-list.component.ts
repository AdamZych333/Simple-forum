import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
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

}
