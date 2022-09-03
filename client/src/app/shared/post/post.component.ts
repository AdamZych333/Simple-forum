import { ChangeDetectionStrategy, Component, Input, Output, EventEmitter } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { AuthService, FollowService, Post, PostService, VoteService, VoteTypes } from 'src/app/core';

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
  @Output() onDelete = new EventEmitter<number>();
  VOTE_UP = VoteTypes.UP;
  VOTE_DOWN = VoteTypes.DOWN;

  authenticatedUser$ = this.authService.getCurrentUser();
  canEdit$ = this.authenticatedUser$.pipe(
    map(authUser => authUser.id === this.post.userID || authUser.roles.map(r => r.name).includes('ADMIN')),
  )

  constructor(
    private postService: PostService,
    private authService: AuthService,
  ) { }

  onPostDelete(){
    this.postService.delete(this.post.id).subscribe({
      next: () => this.onDelete.emit(this.post.id),
    })
  }
}
