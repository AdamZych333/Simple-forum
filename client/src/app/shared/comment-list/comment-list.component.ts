import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

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
