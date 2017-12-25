import {Component, OnInit} from '@angular/core';
import {GameService} from './game.service';
import {Player} from './dto/player';
import {CookieService} from 'angular2-cookie/core';

@Component({
  template: `
    <div *ngIf="doLogin">
      <input id="username-input" [ngModel]="username" placeholder="username" />
      <input id="password-input" [ngModel]="password" placeholder="password" />
      <input *ngIf="doRegister" id="password-conf-input" [ngModel]="passwordConf" placeholder="password confirmation" />
      <button *ngIf="!doRegister" (click)="login(username, password)">Login</button>
      <button *ngIf="doRegister" (click)="register(username, password, passwordConf)">Register</button>
      <button *ngIf="!doRegister" (click)="doRegister=true">New User</button>
      <p *ngIf="showPasswordDoesNotMatchMsg">Passwords do not match</p>
      <p *ngIf="showLoginIssueMsg">Username and passwords do not match. Please make sure you have an account, or register for a new one.</p>
    </div>
  `,
  styleUrls: []
})
export class LandingComponent implements OnInit {
  sessionToken = '';
  doLogin = false;
  doRegister = false;
  player: Player = null;
  showPasswordDoesNotMatchMsg = false;
  showLoginIssueMsg = false;

  constructor(private _cookieService: CookieService, private _gameService: GameService) {}

  ngOnInit(): void {
    this.sessionToken = this._cookieService.get("simple-war-session-token"); // TODO move key to constants.ts file
    if (this.sessionToken) {
      this._gameService.getPlayerFromSessionTokenCookie(this.sessionToken)
        .then((player) => {
        this.player = player;
      }, () => {
        this.doLogin = true;
      })
    } else {
      this.doLogin = true;
    }
  }

  login(username: string, password: string): void {
    // TODO hash password before calling backend
    this._gameService.login(username, password).then(sessionToken => {
      this.sessionToken = sessionToken;
      this._cookieService.put("simple-war-session-token", this.sessionToken); // TODO move key to constants.ts file
      this._gameService.getPlayerFromSessionTokenCookie(this.sessionToken).then(player => {
        this.player = player;
      }, () => {
        this.showLoginIssueMsg = true;
      })
    });
  }

  register(username: string, password: string, passwordConf: string): void {
    // TODO hash password before calling backend
    if (password === passwordConf) {
      this.login(username, password);
    } else {
      this.showPasswordDoesNotMatchMsg = true;
    }
  }
}
