import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { UsersCommentsComponent } from "./users-comments/users-comments.component";
import { UsersFollowsComponent } from "./users-follows/users-follows.component";
import { UsersPostsComponent } from "./users-posts/users-posts.component";
import { UsersResolver } from "./users-resolver.service";
import { UsersComponent } from "./users.component";

const routes: Routes = [
    {
      path: ':id',
      component: UsersComponent,
      resolve: [
        UsersResolver
      ],
      children: [
        {
          path: '',
          component: UsersPostsComponent,
        },
        {
          path: 'comments',
          component: UsersCommentsComponent,
        },
        {
          path: 'follows',
          component: UsersFollowsComponent,
        },
      ]
    },
    {
        path: '**',
        redirectTo: '/' 
    }
  ];
  
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
  export class UsersRoutnigModule {}