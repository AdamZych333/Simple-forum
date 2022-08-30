import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { UsersResolver } from "./users-resolver.service";
import { UsersComponent } from "./users.component";

const routes: Routes = [
    {
      path: ':id',
      component: UsersComponent,
      resolve: [
        UsersResolver
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