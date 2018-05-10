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
var LoginRequest_1 = require("./dto/LoginRequest");
var LoginResponse_1 = require("./dto/LoginResponse");
var RegisterRequest_1 = require("./dto/RegisterRequest");
var LandingComponent = (function () {
    function LandingComponent(_cookieService, _gameService) {
        this._cookieService = _cookieService;
        this._gameService = _gameService;
        this.username = '';
        this.password = '';
        this.passwordConf = '';
        this.email = '';
        this.sessionToken = '';
        this.doLogin = false;
        this.doRegister = false;
        this.showLoggedInContent = false;
        this.showLoginErrorMsg = false;
        this.loginErrorMsg = '';
        this.showPasswordDoesNotMatchMsg = false;
        this.player = null;
    }
    LandingComponent.prototype.ngOnInit = function () {
        this.sessionToken = this._cookieService.get("simple-war-session-token");
        if (this.sessionToken) {
            this.showLoggedInContent = true;
        }
        else {
            this.doLogin = true;
        }
    };
    LandingComponent.prototype.login = function (username, password) {
        var _this = this;
        // TODO hash password before calling backend
        var loginRequest = new LoginRequest_1.LoginRequest();
        loginRequest.username = username;
        loginRequest.password = password;
        this._gameService.login(loginRequest).then(function (loginResponse) {
            _this.sessionToken = loginResponse.sessionToken;
            _this._cookieService.put("simple-war-session-token", _this.sessionToken);
        }).catch(function (error) {
            _this.showLoginErrorMsg = true;
            var loginResponse = Object.assign(new LoginResponse_1.LoginResponse(), JSON.parse(error._body));
            _this.loginErrorMsg = loginResponse.message;
        });
    };
    LandingComponent.prototype.register = function (username, password, passwordConf, email) {
        // TODO hash password before calling backend
        var registerRequest = new RegisterRequest_1.RegisterRequest();
        registerRequest.username = username;
        registerRequest.password = password;
        registerRequest.email = email;
        if (password === passwordConf) {
            this._gameService.register(registerRequest).then(function (registerResponse) {
            }).catch(function (error) {
                // this.showErrorMsg = true;
                // this.errorMsg = error.message;
            });
        }
        else {
            this.showPasswordDoesNotMatchMsg = true;
        }
    };
    return LandingComponent;
}());
LandingComponent = __decorate([
    core_1.Component({
        template: "\n    <div *ngIf=\"doLogin\">\n      <input id=\"username-input\" [(ngModel)]=\"username\" placeholder=\"username\" />\n      <input id=\"password-input\" [(ngModel)]=\"password\" placeholder=\"password\" />\n      <input *ngIf=\"doRegister\" id=\"password-conf-input\" [(ngModel)]=\"passwordConf\" placeholder=\"password confirmation\" />\n      <input *ngIf=\"doRegister\" id=\"email-input\" [(ngModel)]=\"email\" placeholder=\"email address\" />\n      <button *ngIf=\"!doRegister\" (click)=\"login(username, password)\">Login</button>\n      <button *ngIf=\"doRegister\" (click)=\"register(username, password, passwordConf, email)\">Register</button>\n      <button *ngIf=\"!doRegister\" (click)=\"doRegister=true\">New User</button>\n      <p *ngIf=\"showPasswordDoesNotMatchMsg\">Passwords do not match</p>\n      <p id=\"login-error-msg\" *ngIf=\"showLoginErrorMsg\">{{loginErrorMsg}}</p>\n    </div>\n    <div *ngIf=\"showLoggedInContent\">\n    LOGGED IN\n    </div>\n  ",
        styleUrls: []
    }),
    __metadata("design:paramtypes", [core_2.CookieService, game_service_1.GameService])
], LandingComponent);
exports.LandingComponent = LandingComponent;
//# sourceMappingURL=landing.component.js.map