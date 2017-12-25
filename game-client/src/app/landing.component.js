"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var game_service_1 = require("./game.service");
var core_2 = require("angular2-cookie/core");
var LandingComponent = (function () {
    function LandingComponent(_cookieService, _gameService) {
        this._cookieService = _cookieService;
        this._gameService = _gameService;
        this.sessionToken = '';
        this.doLogin = false;
        this.doRegister = false;
        this.player = null;
        this.showPasswordDoesNotMatchMsg = false;
        this.showLoginIssueMsg = false;
    }
    LandingComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.sessionToken = this._cookieService.get("simple-war-session-token");
        if (this.sessionToken) {
            this._gameService.getPlayerFromSessionTokenCookie(this.sessionToken)
                .then(function (player) {
                _this.player = player;
            }, function () {
                _this.doLogin = true;
            });
        }
        else {
            this.doLogin = true;
        }
    };
    LandingComponent.prototype.login = function (username, password) {
        // TODO hash password before calling backend
        var _this = this;
        this._gameService.login(username, password).then(function (sessionToken) {
            _this.sessionToken = sessionToken;
            _this._gameService.getPlayerFromSessionTokenCookie(_this.sessionToken).then(function (player) {
                _this.player = player;
            }, function () {
                _this.showLoginIssueMsg = true;
            });
        });
    };
    LandingComponent.prototype.register = function (username, password, passwordConf) {
        // TODO hash password before calling backend
        if (password === passwordConf) {
            this.login(username, password);
        }
        else {
            this.showPasswordDoesNotMatchMsg = true;
        }
    };
    return LandingComponent;
}());
LandingComponent = __decorate([
    core_1.Component({
        template: "\n    <div *ngIf=\"doLogin\">\n      <input id=\"username-input\" [ngModel]=\"username\" placeholder=\"username\" />\n      <input id=\"password-input\" [ngModel]=\"password\" placeholder=\"password\" />\n      <input *ngIf=\"doRegister\" id=\"password-conf-input\" [ngModel]=\"passwordConf\" placeholder=\"password confirmation\" />\n      <button *ngIf=\"!doRegister\" (click)=\"login(username, password)\">Login</button>\n      <button *ngIf=\"doRegister\" (click)=\"register(username, password, passwordConf)\">Register</button>\n      <button *ngIf=\"!doRegister\" (click)=\"doRegister=true\">New User</button>\n      <p *ngIf=\"showPasswordDoesNotMatchMsg\">Passwords do not match</p>\n      <p *ngIf=\"showLoginIssueMsg\">Username and passwords do not match. Please make sure you have an account, or register for a new one.</p>\n    </div>\n  ",
        styleUrls: []
    }),
    __metadata("design:paramtypes", [core_2.CookieService, game_service_1.GameService])
], LandingComponent);
exports.LandingComponent = LandingComponent;
//# sourceMappingURL=landing.component.js.map