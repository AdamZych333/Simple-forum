import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { PostsResolver } from "./posts-resolver.service";
import { PostsComponent } from "./posts.component";

const routes: Routes = [
    {
      path: ':id',
      component: PostsComponent,
      resolve: {
        post: PostsResolver,
      }
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
  export class PostsRoutnigModule {}