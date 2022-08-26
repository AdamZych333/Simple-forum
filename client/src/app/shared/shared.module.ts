import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsernameButtonComponent } from './buttons/username-button/username-button.component';
import { HeaderComponent, FooterComponent } from './layout';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    UsernameButtonComponent,
    HeaderComponent,
    FooterComponent,
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
  ]
})
export class SharedModule { }
