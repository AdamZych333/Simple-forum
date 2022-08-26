import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsernameButtonComponent } from './buttons/username-button/username-button.component';
import { HeaderComponent, FooterComponent } from './layout';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { FollowButtonComponent } from './buttons/follow-button/follow-button.component';

@NgModule({
  declarations: [
    UsernameButtonComponent,
    HeaderComponent,
    FooterComponent,
    FollowButtonComponent,
  ],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    RouterModule,
  ],
  exports: [
    UsernameButtonComponent,
    HeaderComponent,
    FooterComponent,
    FollowButtonComponent,
  ]
})
export class SharedModule { }
