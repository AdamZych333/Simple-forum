import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AuthService } from './core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppComponent implements OnInit{
  constructor(
    private authService: AuthService
  ){}

  ngOnInit(): void {
    this.authService.initAuth();
  }

}
