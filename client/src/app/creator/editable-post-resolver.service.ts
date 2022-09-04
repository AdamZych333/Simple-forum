import { Injectable } from "@angular/core";
import { Resolve, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { Observable, catchError, EMPTY, tap, switchMap, map } from "rxjs";
import { AuthService, Post, PostService } from "../core";

@Injectable()
export class EditablePostResolver implements Resolve<Post> {

    constructor(
        private postService: PostService,
        private router: Router,
        private authService: AuthService,
    ){}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Post> {

        return this.postService.getById(route.params['id']).pipe(
            switchMap(post => this.authService.getCurrentUser().pipe(
                map(authUser => {
                    if(!(authUser.id === post.userID || authUser.roles.map(r => r.name).includes('ADMIN'))){
                        this.router.navigateByUrl('/');    
                    }

                    return post;            
                }),
            )),
            catchError(() => {
                this.router.navigateByUrl('/');
                return EMPTY;
            }),
        )
    }
    
}