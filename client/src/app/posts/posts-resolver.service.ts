import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from "@angular/router";
import { catchError, EMPTY, map, Observable, tap } from "rxjs";
import { Post, PostService } from "../core";

@Injectable()
export class PostsResolver implements Resolve<Post> {

    constructor(
        private postService: PostService,
        private router: Router,
    ){}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Post> {
        
        return this.postService.getById(route.params['id']).pipe(
            catchError(() => {
                this.router.navigateByUrl('/');
                return EMPTY;
            }),
        )
    }
    
}