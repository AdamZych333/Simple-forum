import { Component, OnInit } from '@angular/core';
import { map, Observable } from 'rxjs';
import { AuthService } from 'src/app/core';

@Component({
  selector: 'layout-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})
export class HeaderComponent implements OnInit {
  isAuthenticated$ = new Observable<boolean>;

  constructor(
    private authService: AuthService
  ){}

  ngOnInit(): void {
    this.isAuthenticated$ = this.authService.inAuthenticatedSubject.asObservable().pipe(
      map(isAuth => !isAuth),
    )
  }

  onSignOutClick(){
    this.authService.removeAuth();
  }
}
