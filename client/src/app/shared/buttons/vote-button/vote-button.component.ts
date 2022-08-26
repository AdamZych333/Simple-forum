import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Votes, VoteService, VoteTypes } from 'src/app/core';

@Component({
  selector: 'app-button-vote[postID][type]',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class VoteButtonComponent implements OnInit {
  @Input() postID: number = 0;
  @Input() type!: VoteTypes;
  votes$!: Observable<Votes>;
  VOTE_UP = VoteTypes.UP;
  VOTE_DOWN = VoteTypes.DOWN;

  constructor(
    private voteService: VoteService,
  ) { }

  ngOnInit(): void {
    this.votes$ = this.voteService.getVotes(this.postID, {type: this.type});
  }

  onVoteClick(){
    this.voteService.votePost(this.postID, {type: this.type});
  }

  onUnvoteClick(){
    this.voteService.unvotePost(this.postID);
  }
}
