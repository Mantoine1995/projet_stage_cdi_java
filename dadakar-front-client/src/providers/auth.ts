import { AuthHttp, JwtHelper } from "angular2-jwt";
import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs";
import { Storage } from "@ionic/storage";

import config from "../assets/config/config";

import "rxjs/add/operator/map";

@Injectable()
export class AuthProvider {

    jwt: string = "";
    public accountId: string = "";

    authUser = new ReplaySubject<any>(1);

    constructor(private readonly http: Http, private readonly authHttp: AuthHttp, private readonly storage: Storage, private readonly jwtHelper: JwtHelper) {}

    checkLogin() {
        //this.storage.get('jwt').then(jwt => {
            if(this.jwt && this.jwtHelper.isTokenExpired(this.jwt)) {
                this.authHttp
                    .get(config.backLoginServerAddress + "/authenticate")
                    .subscribe(() => this.authUser.next(this.jwt), (err) => /*this.storage.remove('jwt').then(() =>*/ this.authUser.next(null))/*)*/;
            } else {
                /*this.storage.remove('jwt').then(() =>*/ this.authUser.next(null)/*)*/;
            }
        //});
    }

    login(values: any): Observable<any> {
            return this.http
                .post(config.backLoginServerAddress + "/login", values)
                .map(response => response.json())
                .map(jwt => this.handleJwtResponse(jwt));
    }

    logout() {
        /*this.storage.remove('jwt').then(() =>*/ this.authUser.next(null)/*)*/;
    }

    // ATTENTION JE NE RECUPERE PAS LE BON OBJET !!!

    signup(values: any): Observable<any> {
        return this.http
            .post(config.backLoginServerAddress + "/signup", values)
            .map(response => response.json())
            .map(jwt => {
                return this.handleJwtResponse(jwt);
            });
    }

    private handleJwtResponse(jwt: any) {
        this.jwt = jwt.token;
        this.accountId = jwt.accountDTO.accountId;
        this.authUser.next(jwt);
        return this.jwt;
        /*return this.storage
            .set('jwt', jwt)
            .then(() => this.authUser.next(jwt))
            .then(() => jwt);*/
    }

}
