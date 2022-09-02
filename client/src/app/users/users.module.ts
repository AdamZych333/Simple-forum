import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from './users.component';
import { UsersRoutnigModule } from './users-routing.module';
import { UsersResolver } from './users-resolver.service';

import { SharedModule } from '../shared';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { UsersPostsComponent } from './users-posts/users-posts.component';
import { UsersCommentsComponent } from './users-comments/users-comments.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

@NgModule({
  declarations: [
    UsersComponent,
    UsersPostsComponent,
    UsersCommentsComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    UsersRoutnigModule,
    MatButtonToggleModule,
    InfiniteScrollModule,
  ],
  providers: [
    UsersResolver,
  ]
})
export class UsersModule { }
