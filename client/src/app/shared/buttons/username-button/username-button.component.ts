import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { map, Observable} from 'rxjs';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-button-username[userID]',
  templateUrl: './username-button.component.html',
  styleUrls: ['./username-button.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UsernameButtonComponent implements OnInit {
  @Input() userID: number = 0;
  username$!: Observable<string>;

  constructor(
    private userService: UserService,
  ) { }

  ngOnInit(): void {
    if(this.userID <= 0){
      return;
    }
    this.username$ = this.userService.getUser(this.userID).pipe(
      map(user => user.name),
    )
  }

}
