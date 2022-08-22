import { Injectable } from "@angular/core";
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { Observable, tap } from "rxjs";
import { AuthService } from "./core";

@Injectable()
export class AppGuard implements CanActivate {
    constructor(
        private authService: AuthService,
        private router: Router,
    ){}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
        
        return this.authService.isAuthenticated().pipe(
            tap(isAuth => {
                if(!isAuth) this.router.navigateByUrl('/login');
            })
        );
    }

}