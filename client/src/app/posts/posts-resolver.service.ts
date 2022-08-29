import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from "@angular/router";
import { catchError, Observable } from "rxjs";
import { Post, PostService } from "../core";

@Injectable()
export class PostsResolver implements Resolve<Post> {

    constructor(
        private postService: PostService,
        private router: Router,
    ){}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        
        return this.postService.getById(route.params['id']).pipe(
            catchError(() => this.router.navigateByUrl('/'))
        )
    }
    
}