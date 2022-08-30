import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models';
import { ApiService } from './api.service';

@Injectable()
export class UserService {

  constructor(
    private apiService: ApiService,
  ) { }

  getUser(userID: number): Observable<User>{
    return this.apiService.get(`/users/${userID}`);
  }

}
