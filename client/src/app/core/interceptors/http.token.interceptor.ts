import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { JwtService } from "../services";

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {
    
    constructor(private jwtService: JwtService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        
        const headersConfig: {[k: string]: string} = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }

        const token = this.jwtService.getToken();
        
        if(token){
            headersConfig['Authorization'] = `Bearer ${token}`;
        }

        const request = req.clone({ setHeaders: headersConfig });

        return next.handle(request);
    }
}