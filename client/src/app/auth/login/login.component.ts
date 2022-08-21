import { Component } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import { AuthService, Errors } from 'src/app/core';

class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null): boolean {
    console.log(control?.invalid)
    return !!(control && control.invalid && control.touched);
  }
}

@Component({
  selector: 'auth-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent {
  email = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);

  errors: Errors = {};
  passwordMatcher = new MyErrorStateMatcher();

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  onSubmit(){
    this.errors = {};
    this.password.disable();
    this.email.disable();

    const credentials = {
      email: this.email.value || '',
      password: this.password.value || '',
    }
    this.authService.login(credentials).subscribe({
      error: () => this.onError(),
      next: () => this.onSuccess(),
    });
  }

  onError(){
    this.password.enable();
    this.email.enable();
    this.password.setErrors({'incorrect': true});
    this.password.markAsTouched();
    this.email.markAsTouched();
  }

  onSuccess(){
    this.router.navigateByUrl('/');
  }
}
