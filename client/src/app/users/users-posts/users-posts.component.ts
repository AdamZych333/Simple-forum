import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map, switchMap, tap } from 'rxjs';
import { Post, PostService, User } from 'src/app/core';

@Component({
  selector: 'app-users-posts',
  templateUrl: './users-posts.component.html',
  styleUrls: ['./users-posts.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UsersPostsComponent {
  user$: Observable<User> = this.route.data.pipe(
    map(data => data['user']),
  );
  createdPosts$ = this.user$.pipe(
    switchMap(user => this.postService.getByUser(user.id)),
  )
  
  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
  ) { }

  trackById(index: number, item: Post){
    return item.id;
  }

}
