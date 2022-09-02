import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-button-post',
  templateUrl: './post-button.component.html',
  styleUrls: ['./post-button.component.sass']
})
export class PostButtonComponent {
  @Input() postID: number = 0;
  
  constructor() { }

}
