import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from './users.component';
import { UsersRoutnigModule } from './users-routing.module';
import { UsersResolver } from './users-resolver.service';



@NgModule({
  declarations: [
    UsersComponent
  ],
  imports: [
    CommonModule,
    UsersRoutnigModule,
  ],
  providers: [
    UsersResolver,
  ]
})
export class UsersModule { }
