import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { HttpTokenInterceptor } from './interceptors';
import { ApiService, AuthService, FollowService, JwtService, TagService } from './services';
import { PostService } from './services/post.service';
import { UserService } from './services/user.service';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpTokenInterceptor, multi: true},
    ApiService,
    JwtService,
    AuthService,
    PostService,
    UserService,
    TagService,
  ]
})
export class CoreModule { }
