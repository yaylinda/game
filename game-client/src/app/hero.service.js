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
var http_1 = require("@angular/http");
require("rxjs/add/operator/toPromise");
require("rxjs/Rx");
var Rx_1 = require("rxjs/Rx");
var HeroService = (function () {
    function HeroService(http) {
        this.http = http;
        this.headers = new http_1.Headers({
            'Content-Type': 'application/json',
            'accept': 'application/json'
        });
        this.baseUrl = 'http://localhost:8080';
        this.playerUrl = '/player';
        this.joinGameUrl = '/player/join';
        this.startGameUrl = '/game/start';
        this.cardUrl = '/game/card';
        this.boardUrl = '/game/board';
        this.pollUrl = '/game/poll';
    }
    HeroService.prototype.joinGame = function (name) {
        var url = "" + this.baseUrl + this.joinGameUrl + "/" + name;
        return this.http
            .post(url, "{}", { headers: this.headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    HeroService.prototype.startGame = function (player1, player2) {
        var url = "" + this.baseUrl + this.startGameUrl;
        return this.http
            .post(url, [player1, player2], { headers: this.headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    HeroService.prototype.getPlayerById = function (id) {
        console.log("getting player by id: " + id);
        var url = "" + this.baseUrl + this.playerUrl + "/" + id;
        return this.http
            .get(url, { headers: this.headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    HeroService.prototype.pollForGame = function (id) {
        var _this = this;
        console.log('polling for game...');
        var url = "" + this.baseUrl + this.pollUrl + "/" + id;
        return Rx_1.Observable
            .fromPromise(this.http
            .get(url, { headers: this.headers })
            .toPromise())
            .flatMap(function (jobQueueData) {
            return Rx_1.Observable.interval(1000)
                .flatMap(function () { return _this.http
                .get(url, { headers: _this.headers })
                .toPromise(); })
                .filter(function (x) {
                return x.json().gameBoard != null;
            })
                .take(1)
                .map(function (gameSession) {
                return gameSession.json();
            })
                .timeout(60000);
        });
    };
    // TODO update board
    // TODO draw cards
    // TODO place card
    HeroService.prototype.handleError = function (error) {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    };
    return HeroService;
}());
HeroService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], HeroService);
exports.HeroService = HeroService;
//# sourceMappingURL=hero.service.js.map