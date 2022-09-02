import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map, switchMap } from 'rxjs';
import { CommentService, PostService, User } from '../core';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.sass']
})
export class UsersComponent {
  user$: Observable<User> = this.route.data.pipe(
    map(data => data[0]),
  );
  follows$ = this.user$.pipe(
    switchMap(user => this.postService.getFollowedPosts(user.id)),
  )
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

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private commentService: CommentService,
  ) { }

}
