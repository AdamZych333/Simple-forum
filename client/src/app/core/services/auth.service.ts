import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable, ReplaySubject} from 'rxjs';
import { AuthenticatedUser } from '../models';
import { ApiService } from './api.service';
import { JwtService } from './jwt.service';

interface ILoginCredencials{
  email: string,
  password: string,
}

interface IRegisterCredencials extends ILoginCredencials{
  name: string,
}

@Injectable()
export class AuthService {
  private currentUserSubject = new ReplaySubject<AuthenticatedUser>(1);

  constructor(
    private apiService: ApiService,
    private jwtService: JwtService,
    private router: Router,
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
      },
      error: () => this.removeAuth(),
    })
  }

  removeAuth(){
    this.jwtService.destroyToken();
    this.currentUserSubject.next({} as AuthenticatedUser);
    
    this.router.navigateByUrl('/login');
  }

  login(credentials: ILoginCredencials): Observable<void>{
    return this.apiService.post('/auth/login', credentials).pipe(
      map(((res: {token: string}) => {
        this.setAuth(res.token);
      })),
    );
  }

  register(credentials: IRegisterCredencials): Observable<void>{
    return this.apiService.post('/auth/register', credentials);
  }

  isAuthenticated(): Observable<boolean>{
    return this.currentUserSubject.asObservable().pipe(
      map(user => {
        return Object.keys(user).length > 0;
      })
    )
  }

  getCurrentUser(): Observable<AuthenticatedUser>{
    return this.currentUserSubject.asObservable();
  }

}
