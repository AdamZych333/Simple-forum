import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { map, Observable, switchMap } from 'rxjs';
import { AuthService, PostService } from 'src/app/core';

@Component({
  selector: 'layout-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderComponent implements OnInit {
  isNotAuthenticated$ = new Observable<boolean>;
  authenticatedUser$ = this.authService.getCurrentUser();
  newActivity$ = this.authenticatedUser$.pipe(
    switchMap(user => this.postService.getFollowedPosts(user.id)),
    map(posts => {
      let sum = 0;
      for(let post of posts){
        if(!post.newActivity) continue;
        sum+=post.newActivity;
      }
      return sum;
    })
  )
  constructor(
    private authService: AuthService,
    private postService: PostService,
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
