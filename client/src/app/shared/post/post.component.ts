import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { FollowService, Post, VoteService, VoteTypes } from 'src/app/core';

@Component({
  selector: 'app-post[post]',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.sass'],
  providers: [
    VoteService,
    FollowService,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PostComponent {
  @Input() post!: Post;
  VOTE_UP = VoteTypes.UP;
  VOTE_DOWN = VoteTypes.DOWN;

  constructor() { }
}
