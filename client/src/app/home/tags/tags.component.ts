import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { map, Observable, tap } from 'rxjs';
import { Tag } from 'src/app/core';
import { TagService } from 'src/app/core/services/tag.service';


@Component({
  selector: 'home-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TagsComponent implements OnInit{
  tags$!: Observable<Tag[]>;

  constructor(
    private tagService: TagService,
  ) { }

  ngOnInit(): void {
    this.tags$ = this.tagService.getPopularTags({});
  }

}
