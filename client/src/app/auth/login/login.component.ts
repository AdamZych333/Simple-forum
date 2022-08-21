import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, Errors } from 'src/app/core';

@Component({
  selector: 'auth-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent {
  email = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);

  isSubmitting = false;
  errors: Errors = {};

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  getEmailErrorMessage() {
    return this.email.hasError('required')? 'You must enter an email': '';
  }

  getPasswordErrorMessage() {
    return this.password.hasError('required')? 'You must enter a password': '';
  }

  onSubmit(){
    console.log("login")

    this.isSubmitting = true;
    this.errors = {};


    const credentials = {
      email: this.email.value || '',
      password: this.password.value || '',
    }
    this.authService.login(credentials).subscribe({
      error: () => this.onError(),
      next: e => console.log(e),
    });
  }

  onError(){
    console.log("error")
  }

  onSuccess(){
    console.log("success")
  }
}
