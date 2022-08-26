import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input } from '@angular/core';
import { PostService } from 'src/app/core/services/post.service';

@Component({
  selector: 'app-button-follow[postID]',
  templateUrl: './follow-button.component.html',
  styleUrls: ['./follow-button.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FollowButtonComponent {
  @Input() postID: number = 0;
  count: number = 0;
  isFollowed: boolean = false;

  constructor(
    private postService: PostService,
    private changeDetectorRef: ChangeDetectorRef,
  ) { }

  onFollowClick(){
    if(this.postID <= 0) return;
    this.postService.followPost(this.postID).subscribe({
      next: () => {
        this.count++;
        this.isFollowed = true;
        this.changeDetectorRef.detectChanges();
      },
    });
    
  }

  onUnfollowClick(){
    if(this.postID <= 0) return;
    this.postService.unfollowPost(this.postID).subscribe({
      next: () => {
        this.count--;
        this.isFollowed = false;
        this.changeDetectorRef.detectChanges();
      },
    });
  }
}
