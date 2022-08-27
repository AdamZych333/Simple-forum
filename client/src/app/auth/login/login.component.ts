import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { first } from 'rxjs';
import { AuthService } from 'src/app/core';


@Component({
  selector: 'auth-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
  email = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);


  constructor(
    private authService: AuthService,
    private router: Router,
  ) {
  }

  onSubmit(){
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
    this.password.enable();
    this.email.enable();
  }

  onError(){
    this.password.setErrors({'incorrect': true});
    this.password.markAsTouched();
    this.email.markAsTouched();
  }

  onSuccess(){
    this.authService.isAuthenticated().pipe(
      first(value => value === true),
    ).subscribe({
      next: (e: boolean) => {
        if(e) this.router.navigateByUrl('/');
      },
      error: () => {
        console.log("Unexpected error");
        this.password.setValue('');
        this.email.setValue('');
      }
    })
    
  }
}
