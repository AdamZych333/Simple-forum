import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map, Observable } from 'rxjs';
import { Post } from '../core';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.sass']
})
export class PostsComponent {
  post$: Observable<Post> = this.route.data.pipe(
    map(data => data['post'])
  );

  constructor(
    private route: ActivatedRoute,

  ) { }

}
