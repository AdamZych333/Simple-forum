import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map } from 'rxjs';
import { User } from '../core';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.sass']
})
export class UsersComponent implements OnInit {
  user$: Observable<User> = this.route.data.pipe(
    map(data => data['user'])
  );

  constructor(
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
  }

}
