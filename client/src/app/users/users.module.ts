import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from './users.component';
import { UsersRoutnigModule } from './users-routing.module';
import { UsersResolver } from './users-resolver.service';

import {MatDividerModule} from '@angular/material/divider';
import {MatTabsModule} from '@angular/material/tabs';
import { SharedModule } from '../shared';

@NgModule({
  declarations: [
    UsersComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    UsersRoutnigModule,
    MatDividerModule,
    MatTabsModule,
  ],
  providers: [
    UsersResolver,
  ]
})
export class UsersModule { }
