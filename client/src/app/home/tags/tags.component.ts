import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Tag } from 'src/app/core';
import { TagService } from 'src/app/core/services/tag.service';


@Component({
  selector: 'home-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.sass']
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
