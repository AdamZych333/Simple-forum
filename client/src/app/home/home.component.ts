import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Post, Tag } from '../core';
import { IPostQueryParams, PostService } from '../core/services/post.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent implements OnInit{
  DEFAULT_TITLE = 'Recent posts'
  title: string = this.DEFAULT_TITLE;
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
    this.DEFAULT_TITLE;
  }

  searchByTag(tag: Tag){
    this.posts$ = this.postService.getByTag(tag.id);
    
    this.title = `Posts with tag: #${tag.name}`;
  }

  onCancelClick(){
    this.params = {}
    this.searchPosts();
  }
}
