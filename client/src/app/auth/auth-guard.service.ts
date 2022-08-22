import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from "@angular/router";
import { Observable, of } from "rxjs";
import { AuthService } from "../core";
import { map, take, tap } from 'rxjs/operators';

@Injectable()
export class AuthGuard implements CanActivate {
    constructor(
        private authService: AuthService,
        private router: Router,
    ){}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
        
        return this.authService.inAuthenticatedSubject.asObservable().pipe(
            take(1),
            map(isAuth => !isAuth),
            tap(isAuth => {
                if(!isAuth) this.router.navigateByUrl('/');
            })
        );
    }

}