import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map, tap, switchMap } from 'rxjs';
import { PostService, User } from '../core';

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

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
  ) { }

  ngOnInit(): void {
  }

}
