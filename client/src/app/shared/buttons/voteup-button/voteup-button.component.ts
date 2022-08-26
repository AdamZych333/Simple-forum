import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Votes, VoteService } from 'src/app/core';

@Component({
  selector: 'app-button-voteup[postID]',
  templateUrl: './voteup-button.component.html',
  styleUrls: ['./voteup-button.component.sass']
})
export class VoteupButtonComponent implements OnInit {
  @Input() postID: number = 0;
  votesUp$!: Observable<Votes>;

  constructor(
    private voteService: VoteService,
  ) { }

  ngOnInit(): void {
  }

}
