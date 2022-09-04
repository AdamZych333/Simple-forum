import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map, switchMap } from 'rxjs';
import { AuthService, PostService, User } from '../core';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UsersComponent {
  user$: Observable<User> = this.route.data.pipe(
    map(data => data['user']),
  );
  newActivity$ = this.user$.pipe(
    switchMap(user => this.postService.getFollowedPosts(user.id)),
    map(posts => {
      let sum = 0;
      for(let post of posts){
        if(!post.newActivity) continue;
        sum+=post.newActivity;
      }
      return sum;
    })
  )
  isOwner$ = this.user$.pipe(
    switchMap(user => this.authService.getCurrentUser().pipe(
      map(authUser => authUser.id === user.id),
    ))
  )

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private authService: AuthService,
  ) { }

}
