import { Component } from '@angular/core';
import {COMMA, ENTER, SPACE} from '@angular/cdk/keycodes';
import { FormControl, Validators } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';

@Component({
  selector: 'app-creator',
  templateUrl: './creator.component.html',
  styleUrls: ['./creator.component.sass']
})
export class CreatorComponent {
  titleForm = new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(30)]);
  contentForm = new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(500)]);
  tagsForm = new FormControl('');
  readonly separatorKeyCodes = [ENTER, COMMA, SPACE] as const;
  selectedTags: string[] = [];

  constructor() { }

  addTag(event: MatChipInputEvent): void {
    const value = (event.value || '');

    if(value){
      this.selectedTags.push(value);
    }

    event.chipInput!.clear();
  }

  removeTag(tag: string){
    const index = this.selectedTags.indexOf(tag);

    if(index >= 0){
      this.selectedTags.splice(index, 1);
    }
  }

  onSubmit(){
    console.log('submit');
  }
}
