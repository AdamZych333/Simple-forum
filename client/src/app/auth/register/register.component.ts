import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, Errors } from 'src/app/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RegisterComponent {
  email = new FormControl('', [Validators.required]);
  name = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);
  confirmPassword = new FormControl('', []);

  errors: Errors = {};

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  getEmailErrorMessage(){
    if(this.email.hasError('required')){
      return 'You must enter an email';
    }
    
    return this.email.hasError('incorrect')? this.errors['email'][0]: '';
  }

  getNameErrorMessage(){
    if(this.name.hasError('required')){
      return 'You must enter a name';
    }

    return this.name.hasError('incorrect')? this.errors['name'][0]: '';
  }

  getPasswordErrorMessage(){
    if(this.password.hasError('required')){
      return 'You must enter a password';
    }
    
    return this.password.hasError('incorrect')? this.errors['password'][0]: '';
  }

  onPasswordsChange(){
    if(!this.confirmPassword.value) return;

    if(this.confirmPassword.value !== this.password.value){
      this.confirmPassword.setErrors({'notMatching': true});
    }else{
      this.confirmPassword.setErrors(null);
    }
  }

  onSubmit(){
    this.email.disable();
    this.name.disable();
    this.password.disable();
    this.confirmPassword.disable();

    const credentials = {
      email: this.email.value || '',
      name: this.name.value || '',
      password: this.password.value || '',
    }
    this.authService.register(credentials).subscribe({
      error: (err) => this.onError(err.error.message),
      next: () => this.onSuccess(),
    });

    this.email.enable();
    this.name.enable();
    this.password.enable();
    this.confirmPassword.enable();
  }

  onError(errors: Errors){  
    this.errors = errors;

    if(errors?.['email'] && this.email.value){
      this.email.setErrors({'incorrect': true});
    }
    if(errors?.['name'] && this.name.value){
      this.name.setErrors({'incorrect': true});
    }
    if(errors?.['password'] && this.password.value){
      this.password.setErrors({'incorrect': true});
    }

    this.password.markAsTouched();
    this.email.markAsTouched();
    this.name.markAsTouched();
    this.confirmPassword.markAsTouched();
  }

  onSuccess(){
    this.router.navigateByUrl('/login');
  }
}
