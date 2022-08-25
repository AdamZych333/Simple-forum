import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../core';
import { PostService } from '../core/services/post.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass'],
})
export class HomeComponent implements OnInit{
  query: string = "";
  title: string = 'Recent posts';
  posts$!: Observable<Post[]>;

  constructor(
    private postService: PostService
  ){}

  ngOnInit(): void {
    this.posts$ = this.postService.queryPosts({});
  }
 
}
