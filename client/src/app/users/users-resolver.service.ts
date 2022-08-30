import { Injectable } from "@angular/core";
import { Resolve, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { Observable, catchError, map, of, EMPTY } from "rxjs";
import { User } from "../core";
import { UserService } from "../core/services/user.service";

@Injectable()
export class UsersResolver implements Resolve<User> {

    constructor(
        private userService: UserService,
        private router: Router,
    ){}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User> {
        
        return this.userService.getUser(route.params['id']).pipe(
            catchError(() => {
                this.router.navigateByUrl('/');
                return EMPTY;
            }),
        )
    }
    
}