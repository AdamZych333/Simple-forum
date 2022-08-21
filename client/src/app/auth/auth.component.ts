import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.sass']
})
export class AuthComponent {
  @Input() name: String = 'Name'
  @Input() linkName: String = 'Link'
  @Input() linkTo: String = '/'
  @Output() onSubmit = new EventEmitter();

  constructor() { }


  submit(){
    this.onSubmit.emit();
  }
}
