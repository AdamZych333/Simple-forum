import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../core';
import { VoteType } from '../core/models/vote.model';
import { PostService } from '../core/services/post.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit{
  query: string = "";
  title: string = 'Recent posts';
  posts$!: Observable<Post[]>;

  constructor(
    private postService: PostService
  ){}

  ngOnInit(): void {
    this.posts$ = this.postService.queryPosts({});
  }

  getPostVoteUp(post: Post){
    const vote = post.votes.find(vote => vote.type === VoteType.UP);
    if(!vote) return 0;
    return vote.count;
  }

  getPostVoteDown(post: Post){
    const vote = post.votes.find(vote => vote.type === VoteType.DOWN);
    if(!vote) return 0;
    return vote.count;
  }
}
