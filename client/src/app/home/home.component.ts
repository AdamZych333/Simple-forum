import { I } from '@angular/cdk/keycodes';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { BehaviorSubject, map, Observable, switchMap, tap } from 'rxjs';
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
  private pageSubject = new BehaviorSubject<number>(0);
  
  posts$: Observable<Post[]> = this.pageSubject.pipe(
    tap(page => {
      if(page === 0) this.finnished = false;
      this.params = {...this.params, page: 0, pageSize: (page+1)*this.PAGE_SIZE}
      this.updateTitle();
    }),
    switchMap(() => this.postService.queryPosts(this.selectedTag, this.params,)),
    tap(posts => {
      if(posts.length%this.PAGE_SIZE != 0) this.finnished = true;
    }),
  );

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
