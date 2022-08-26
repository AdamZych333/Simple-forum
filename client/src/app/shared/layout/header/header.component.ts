import { Component, OnInit } from '@angular/core';
import { map, Observable } from 'rxjs';
import { AuthService } from 'src/app/core';
import { Router } from '@angular/router';

@Component({
  selector: 'layout-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})
export class HeaderComponent implements OnInit {
  isNotAuthenticated$ = new Observable<boolean>;

  constructor(
    private authService: AuthService,
    private router: Router,
  ){}

  ngOnInit(): void {
    this.isNotAuthenticated$ = this.authService.isAuthenticated().pipe(
      map(isAuth => !isAuth),
    )
  }

  onSignOutClick(){
    this.authService.removeAuth();
  }
}
