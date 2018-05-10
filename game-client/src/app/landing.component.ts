import {Component, OnInit} from '@angular/core';
import {GameService} from './game.service';
import {Player} from './dto/player';
import {CookieService} from 'angular2-cookie/core';
import { LoginRequest } from './dto/LoginRequest';
import { LoginResponse } from './dto/LoginResponse';
import { RegisterRequest } from './dto/RegisterRequest';
import { RegisterResponse } from './dto/RegisterResponse';

@Component({
  template: `
    <div *ngIf="doLogin">
      <input id="username-input" [(ngModel)]="username" placeholder="username" />
      <input id="password-input" [(ngModel)]="password" placeholder="password" />
      <input *ngIf="doRegister" id="password-conf-input" [(ngModel)]="passwordConf" placeholder="password confirmation" />
      <input *ngIf="doRegister" id="email-input" [(ngModel)]="email" placeholder="email address" />
      <button *ngIf="!doRegister" (click)="login(username, password)">Login</button>
      <button *ngIf="doRegister" (click)="register(username, password, passwordConf, email)">Register</button>
      <button *ngIf="!doRegister" (click)="doRegister=true">New User</button>
      <p *ngIf="showPasswordDoesNotMatchMsg">Passwords do not match</p>
      <p id="login-error-msg" *ngIf="showLoginErrorMsg">{{loginErrorMsg}}</p>
    </div>
    <div *ngIf="showLoggedInContent">
    LOGGED IN
    </div>
  `,
  styleUrls: []
})
export class LandingComponent implements OnInit {
  username: string = '';
  password: string = '';
  passwordConf: string = '';
  email: string = '';
  sessionToken = '';
  doLogin = false;
  doRegister = false;
  showLoggedInContent = false;
  showLoginErrorMsg = false;
  loginErrorMsg = '';
  showPasswordDoesNotMatchMsg = false;

  player: Player = null;

  constructor(private _cookieService: CookieService, private _gameService: GameService) {}

  ngOnInit(): void {
    this.sessionToken = this._cookieService.get("simple-war-session-token");
    if (this.sessionToken) {
      this.showLoggedInContent = true;
    } else {
      this.doLogin = true;
    }
  }

  login(username: string, password: string): void {
    // TODO hash password before calling backend
    let loginRequest = new LoginRequest();
    loginRequest.username = username;
    loginRequest.password = password;

    this._gameService.login(loginRequest).then(loginResponse => {
      this.sessionToken = loginResponse.sessionToken;
      this._cookieService.put("simple-war-session-token", this.sessionToken);
    }).catch(error => {
      this.showLoginErrorMsg = true;
      let loginResponse: LoginResponse = Object.assign(new LoginResponse(), JSON.parse(error._body));
      this.loginErrorMsg = loginResponse.message;
    });
  }

  register(username: string, password: string, passwordConf: string, email: string): void {
    // TODO hash password before calling backend
    let registerRequest = new RegisterRequest();
    registerRequest.username = username;
    registerRequest.password = password;
    registerRequest.email = email;

    if (password === passwordConf) {
      this._gameService.register(registerRequest).then(registerResponse => {

      }).catch(error => {
        // this.showErrorMsg = true;
        // this.errorMsg = error.message;
      });
    } else {
      this.showPasswordDoesNotMatchMsg = true;
    }
  }
}
