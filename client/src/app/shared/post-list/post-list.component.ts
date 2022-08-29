import { Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from 'src/app/core';

@Component({
  selector: 'app-post-list[posts]',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.sass']
})
export class PostListComponent {
  @Input('posts') posts$!: Observable<Post[]>;

  constructor() { }

  trackById(index: number, item: Post){
    return item.id;
  }
}
