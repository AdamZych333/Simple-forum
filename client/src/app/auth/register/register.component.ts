import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass']
})
export class RegisterComponent {
  email = new FormControl('', [Validators.required]);
  name = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);
  confirmPassword = new FormControl('', []);


  constructor() {}

  getEmailErrorMessage() {
    return this.email.hasError('required')? 'You must enter an email': '';
  }

  getNameErrorMessage() {
    return this.name.hasError('required')? 'You must enter a name': '';
  }

  getPasswordErrorMessage() {
    return this.password.hasError('required')? 'You must enter a password': '';
  }

  getConfirmPasswordErrorMessage() {
    return this.confirmPassword.value !== this.password.value? 'The password and confirmation password do not match': '';
  }

  onSubmit(){
    console.log("register")
  }
}
