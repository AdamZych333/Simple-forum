import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'auth-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent {
  email = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);


  constructor() {}

  getEmailErrorMessage() {
    return this.email.hasError('required')? 'You must enter an email': '';
  }

  getPasswordErrorMessage() {
    return this.password.hasError('required')? 'You must enter a password': '';
  }

  onSubmit(){
    console.log("login")
  }
}
