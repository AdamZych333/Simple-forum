import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { User } from '../models';
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
  private currentUserSubject = new BehaviorSubject<User>({} as User);

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
    this.apiService.get('/auth/user').subscribe({
      next: data => {
        const user: User = data;
        user.token = token;
        this.currentUserSubject.next(user);
      },
      error: () => this.removeAuth(),
    })
  }

  removeAuth(){
    this.jwtService.destroyToken();

    this.currentUserSubject.next({} as User);
  }

  login(credentials: loginCredencials): Observable<void>{
    return this.apiService.post('/auth/login', credentials).pipe(
      map((res: {token: string}) => {
        this.jwtService.saveToken(res.token);
        this.setAuth(res.token);
      })
    );
  }

  register(credentials: registerCredencials): Observable<void>{
    return this.apiService.post('/auth/register', credentials);
  }

  getCurrentUser(): User{
    return this.currentUserSubject.value;
  }

}
