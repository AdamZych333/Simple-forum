import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthComponent } from './auth.component';
import { SharedModule } from '../shared';



@NgModule({
  declarations: [
    AuthComponent
  ],
  imports: [
    SharedModule
  ]
})
export class AuthModule { }
