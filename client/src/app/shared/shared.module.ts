import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsernameButtonComponent } from './buttons/username-button/username-button.component';
import { HeaderComponent, FooterComponent } from './layout';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { FollowButtonComponent, VoteButtonComponent } from './buttons';
import { PostComponent } from './post/post.component';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommentComponent } from './comment/comment.component';
import { CommentListComponent } from './comment-list/comment-list.component';
import { CountChildrenPipe } from './comment/count-children.pipe';
import { CommentFormComponent } from './comment-form/comment-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import {MatChipsModule} from '@angular/material/chips';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import {MatMenuModule} from '@angular/material/menu';

@NgModule({
  declarations: [
    UsernameButtonComponent,
    HeaderComponent,
    FooterComponent,
    FollowButtonComponent,
    VoteButtonComponent,
    PostComponent,
    CommentComponent,
    CommentListComponent,
    CountChildrenPipe,
    CommentFormComponent,
  ],
  imports: [
    CommonModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    RouterModule,
    MatCardModule,
    MatChipsModule,
    InfiniteScrollModule,
    MatMenuModule,
  ],
  exports: [
    UsernameButtonComponent,
    HeaderComponent,
    FooterComponent,
    FollowButtonComponent,
    VoteButtonComponent,
    PostComponent,
    CommentComponent,
    CommentListComponent,
    CommentFormComponent,
  ]
})
export class SharedModule { }
