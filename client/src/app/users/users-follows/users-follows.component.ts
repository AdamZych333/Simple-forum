import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map, switchMap } from 'rxjs';
import { PostService, User, Post } from 'src/app/core';

@Component({
  selector: 'app-users-follows',
  templateUrl: './users-follows.component.html',
  styleUrls: ['./users-follows.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UsersFollowsComponent {
  user$: Observable<User> | undefined = this.route.parent?.data.pipe(
    map(data => data['user']),
  );
  follows$ = this.user$?.pipe(
    switchMap(user => this.postService.getFollowedPosts(user.id)),
  )
  
  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
  ) { }

  trackById(index: number, item: Post){
    return item.id;
  }

}
