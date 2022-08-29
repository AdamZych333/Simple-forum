import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostsComponent } from './posts.component';
import { PostsRoutnigModule } from './posts-routing.module';
import { PostsResolver } from './posts-resolver.service';
import { SharedModule } from '../shared';



@NgModule({
  declarations: [
    PostsComponent
  ],
  imports: [
    CommonModule,
    PostsRoutnigModule,
    SharedModule,
  ],
  providers: [
    PostsResolver,
  ]
})
export class PostsModule { }
