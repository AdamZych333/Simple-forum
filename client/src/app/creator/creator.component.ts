import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import {COMMA, ENTER, SPACE} from '@angular/cdk/keycodes';
import { FormControl, Validators } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { IPostBody, PostService } from '../core/services/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Tag } from '../core';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-creator',
  templateUrl: './creator.component.html',
  styleUrls: ['./creator.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CreatorComponent implements OnInit{
  MAX_NUMBER_OF_TAGS = 5;
  MAX_TAG_LENGTH = 15;
  titleForm = new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]);
  contentForm = new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(500)]);
  tagsForm = new FormControl('');
  readonly separatorKeyCodes = [ENTER, COMMA, SPACE] as const;
  selectedTags: string[] = [];
  postID: number | null = null;

  constructor(
    private postService: PostService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.route.data.subscribe({
      next: (data) => {
        if(!data['post']) return;
        this.titleForm.setValue(data['post'].title);
        this.contentForm.setValue(data['post'].content);
        this.selectedTags = data['post'].tags.map((t: Tag) => t.name);
        this.postID = data['post'].id;
      }
    })
  }

  addTag(event: MatChipInputEvent): void {
    const value = (event.value || '').replaceAll(/[^a-zA-Z0-9]/g, '').toLowerCase();
    if(value.length > this.MAX_TAG_LENGTH) return;
    if(value){
      this.selectedTags.push(value);
    }

    event.chipInput!.clear();
    if(this.selectedTags.length >= this.MAX_NUMBER_OF_TAGS){
      this.tagsForm.disable();
    }
  }

  removeTag(tag: string){
    const index = this.selectedTags.indexOf(tag);

    if(index >= 0){
      this.selectedTags.splice(index, 1);
    }
    if(this.tagsForm.disabled && this.selectedTags.length < this.MAX_NUMBER_OF_TAGS){
      this.tagsForm.enable();
    }
  }

  onSubmit(){
    if(this.titleForm.invalid || this.contentForm.invalid) return;

    const body: IPostBody = {
      title: this.titleForm.value || '', 
      content: this.contentForm.value || '',
      tags: this.selectedTags.map(tag => {return {name: tag}}),
    }

    const action = this.postID? 
      this.postService.edit(this.postID, body):
      this.postService.add(body);
    action.subscribe({
      next: () => this.router.navigateByUrl(''),
      error: (e) => console.log(e),
    })
  }

}
