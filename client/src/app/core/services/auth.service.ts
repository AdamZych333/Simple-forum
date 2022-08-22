import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, distinctUntilChanged, map, Observable, of, ReplaySubject, switchMap, tap } from 'rxjs';
import { AuthenticatedUser } from '../models';
import { ApiService } from './api.service';
import { JwtService } from './jwt.service';

interface loginCredencials{
  email: string,
  password: string,
}

interface registerCredencials extends loginCredencials{
  name: string,
}

@Injectable()
export class AuthService {
  public currentUserSubject = new BehaviorSubject<AuthenticatedUser>({} as AuthenticatedUser);
  public inAuthenticatedSubject = new ReplaySubject<boolean>(1);

  constructor(
    private apiService: ApiService,
    private jwtService: JwtService,
  ) { }
    
  initAuth(){
    const token: string = this.jwtService.getToken();
    if(token){
      this.setAuth(token);
    } else {
      this.removeAuth();
    }
  }

  setAuth(token: string){
    this.jwtService.saveToken(token);
    this.apiService.get('/auth/user').subscribe({
      next: (res) => {
        const user: AuthenticatedUser = res;
        user.token = token;
        this.currentUserSubject.next(user);
        this.inAuthenticatedSubject.next(true);
        console.log('logged')
      },
      error: () => this.removeAuth(),
    })
  }

  removeAuth(){
    this.jwtService.destroyToken();

    this.currentUserSubject.next({} as AuthenticatedUser);
    this.inAuthenticatedSubject.next(false);
  }

  login(credentials: loginCredencials): Observable<void>{
    return this.apiService.post('/auth/login', credentials).pipe(
      map(((res: {token: string}) => {
        this.setAuth(res.token);
      })),
    );
  }

  register(credentials: registerCredencials): Observable<void>{
    return this.apiService.post('/auth/register', credentials);
  }

  getCurrentUser(): AuthenticatedUser{
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean{
    return Object.keys(this.currentUserSubject.value).length > 0;
  }

}
