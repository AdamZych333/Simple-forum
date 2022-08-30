import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { map, Observable } from 'rxjs';
import { AuthService } from 'src/app/core';

@Component({
  selector: 'layout-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderComponent implements OnInit {
  isNotAuthenticated$ = new Observable<boolean>;
  authenticatedUser$ = this.authService.getCurrentUser();

  constructor(
    private authService: AuthService,
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
