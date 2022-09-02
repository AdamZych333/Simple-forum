import { ChangeDetectionStrategy, Component, EventEmitter, OnInit, Output } from '@angular/core';
import { BehaviorSubject, Observable, switchMap } from 'rxjs';
import { Tag } from 'src/app/core';
import { TagService } from 'src/app/core/services/tag.service';


@Component({
  selector: 'home-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TagsComponent{
  SHOW_LESS_PAGE_SIZE = 5;
  SHOW_MORE_PAGE_SIZE = 10;
  @Output() onTagSelect = new EventEmitter<Tag>();
  private showMoreSubject = new BehaviorSubject<boolean>(false);
  tags$: Observable<Tag[]> = this.showMoreSubject.pipe(
    switchMap(showMore => this.tagService.getPopularTags({
      pageSize: showMore? this.SHOW_MORE_PAGE_SIZE: this.SHOW_LESS_PAGE_SIZE,
    }))
  )
  showMore$ = this.showMoreSubject.asObservable();

  constructor(
    private tagService: TagService,
  ) { }

  seeMoreToggle(){
    this.showMoreSubject.next(!this.showMoreSubject.getValue());
  }
}
