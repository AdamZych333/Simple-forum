import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { BehaviorSubject, map, switchMap, tap } from 'rxjs';
import { IPostQueryParams, Post, PostService, Tag } from '../core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent {
  DEFAULT_TITLE = 'Recent posts'
  title: string = this.DEFAULT_TITLE;
  PAGE_SIZE = 5;
  params: IPostQueryParams = {
    pageSize: this.PAGE_SIZE,
  };
  selectedTag: Tag | null = null;
  finnished = false;

  curPosts = [] as Post[];
  private pageSubject = new BehaviorSubject<number>(0);
  posts$ = this.pageSubject.pipe(
    tap(page => {
      if(page === 0) {
        this.finnished = false;
        this.curPosts = [] as Post[];
      }
      this.params = {...this.params, page: page}
      this.updateTitle();
    }),
    switchMap(() => this.postService.queryPosts(this.selectedTag, this.params,)),
    tap(newPosts => {
      if(newPosts.length%this.PAGE_SIZE != 0) this.finnished = true;
    }),
    map(newPosts => [...this.curPosts, ...newPosts]),
    tap((posts) => this.curPosts = posts)
  )

  constructor(
    private postService: PostService
  ){}

  onSearch(){
    this.pageSubject.next(0);
  }

  onTagSelect(tag: Tag){
    if(tag === this.selectedTag) return;
    this.selectedTag = tag;
    this.pageSubject.next(0);
  }

  onCancelClick(){
    this.selectedTag = null;
    this.params = {
      pageSize: this.PAGE_SIZE,
    };
    this.pageSubject.next(0);
  }

  onScrolled(){
    if(this.finnished) return;
    this.pageSubject.next(this.pageSubject.getValue()+1);

  }

  trackById(index: number, item: Post){
    return item.id;
  }

  private updateTitle(){
    if(this.selectedTag){
      this.title = `Posts with tag: #${this.selectedTag.name}`;
    }else if(this.params.query){
      this.title =  `Results of search: ${this.params.query}`;
    }else{
      this.title = this.DEFAULT_TITLE;  
    }
  }

}
