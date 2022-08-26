import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Follow, FollowService } from 'src/app/core';

@Component({
  selector: 'app-button-follow[postID]',
  templateUrl: './follow-button.component.html',
  styleUrls: ['./follow-button.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FollowButtonComponent implements OnInit{
  @Input() postID: number = 0;
  follows$!: Observable<Follow>;

  constructor(
    private followService: FollowService,
  ) { }

  ngOnInit(): void {
    this.follows$ = this.followService.getFollows(this.postID); 
  }

  onFollowClick(){
    this.followService.followPost(this.postID);    
  }

  onUnfollowClick(){
    this.followService.unfollowPost(this.postID);
  }
}
