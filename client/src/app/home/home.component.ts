import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { BehaviorSubject, map, Observable, of } from 'rxjs';
import { Post } from '../core';
import { IPostQueryParams, PostService } from '../core/services/post.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass'],
})
export class HomeComponent implements OnInit{
  title: string = 'Recent posts';
  posts$!: Observable<Post[]>;
  params: IPostQueryParams = {};

  constructor(
    private postService: PostService
  ){}

  ngOnInit(): void {
    this.searchPosts();
  }
 
  searchPosts(){
    this.posts$ = this.postService.queryPosts(this.params);

    this.title = this.params.query?
      `Results of search: ${this.params.query}`:
      "Recent posts";
  }
}
