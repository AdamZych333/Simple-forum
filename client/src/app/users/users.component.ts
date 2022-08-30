import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map, tap, switchMap } from 'rxjs';
import { CommentService, PostService, User } from '../core';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.sass']
})
export class UsersComponent implements OnInit {
  user$: Observable<User> = this.route.data.pipe(
    map(data => data[0]),
  );
  createdPosts$ = this.user$.pipe(
    switchMap(user => this.postService.getByUser(user.id)),
  )
  createdComments$ = this.user$.pipe(
    switchMap(user => this.commentService.getByUser(user.id)),
  )
  follows$ = this.user$.pipe(
    switchMap(user => this.postService.getFollowedPosts(user.id)),
  )
  newActivity$ = this.follows$.pipe(
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

  ngOnInit(): void {
  }

}
