import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Follow, FollowService } from 'src/app/core';

@Component({
  selector: 'app-button-follow[postID]',
  templateUrl: './follow-button.component.html',
  styleUrls: ['./follow-button.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    FollowService,
  ]
})
export class FollowButtonComponent implements OnInit{
  @Input() postID: number = 0;
  follows$!: Observable<Follow>;

  constructor(
    private followService: FollowService,
    private changeDetectorRef: ChangeDetectorRef,
  ) { }

  ngOnInit(): void {
    this.follows$ = this.followService.getFollows(this.postID); 
  }

  onFollowClick(){
    this.followService.followPost(this.postID);
    // if(this.postID <= 0) return;
    // this.followService.followPost(this.postID).subscribe({
    //   next: () => {
    //     this.count++;
    //     this.isFollowed = true;
    //     this.changeDetectorRef.detectChanges();
    //   },
    // });
    
  }

  onUnfollowClick(){
    this.followService.unfollowPost(this.postID);
    // if(this.postID <= 0) return;
    // this.followService.unfollowPost(this.postID).subscribe({
    //   next: () => {
    //     this.count--;
    //     this.isFollowed = false;
    //     this.changeDetectorRef.detectChanges();
    //   },
    // });
  }
}
