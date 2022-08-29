import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { Comment } from 'src/app/core';

@Component({
  selector: 'app-comment[comment]',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CommentComponent {
  @Input() comment!: Comment;

  constructor() { }

}
