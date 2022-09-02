import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from './users.component';
import { UsersRoutnigModule } from './users-routing.module';
import { UsersResolver } from './users-resolver.service';

import { SharedModule } from '../shared';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { UsersPostsComponent } from './users-posts/users-posts.component';

@NgModule({
  declarations: [
    UsersComponent,
    UsersPostsComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    UsersRoutnigModule,
    MatButtonToggleModule,
  ],
  providers: [
    UsersResolver,
  ]
})
export class UsersModule { }
