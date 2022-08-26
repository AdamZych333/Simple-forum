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

@NgModule({
  declarations: [
    UsernameButtonComponent,
    HeaderComponent,
    FooterComponent,
    FollowButtonComponent,
    VoteButtonComponent,
    PostComponent,
  ],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    RouterModule,
  ],
  exports: [
    UsernameButtonComponent,
    HeaderComponent,
    FooterComponent,
    FollowButtonComponent,
    VoteButtonComponent,
  ]
})
export class SharedModule { }
