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
        this.updateHandEE = new core_1.EventEmitter();
        this.updatePowerEE = new core_1.EventEmitter();
        this.updateBoardEE = new core_1.EventEmitter();
        this.updateGameSessionEE = new core_1.EventEmitter();
    }
    HeroService.prototype.joinGame = function (name) {
        var url = "" + this.baseUrl + this.joinGameUrl + "/" + name;
        return this.http
            .post(url, "{}", { headers: this.headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    HeroService.prototype.startGame = function (player1, player2, id) {
        console.log("starting game: " + id);
        var url = "" + this.baseUrl + this.startGameUrl + "/" + id;
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
                return x.json().gameboard != null;
            })
                .take(1)
                .map(function (gameSession) {
                return gameSession.json();
            })
                .timeout(600000);
        });
    };
    HeroService.prototype.drawCard = function (id) {
        console.log('drawing card...');
        var url = "" + this.baseUrl + this.cardUrl + "/" + id;
        return this.http
            .get(url, { headers: this.headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    HeroService.prototype.endTurn = function (gameSession) {
        var url = "" + this.baseUrl + this.boardUrl;
        return this.http
            .put(url, gameSession, { headers: this.headers })
            .toPromise()
            .then()
            .catch(this.handleError);
    };
    HeroService.prototype.setClickedCard = function (card) {
        this.selectedCard = card;
    };
    HeroService.prototype.getClickedCard = function () {
        return this.selectedCard;
    };
    HeroService.prototype.updateHand = function (newCard) {
        this.updateHandEE.emit(newCard);
    };
    HeroService.prototype.getUpdatedHand = function () {
        return this.updateHandEE;
    };
    HeroService.prototype.updatePower = function (power) {
        this.updatePowerEE.emit(power);
    };
    HeroService.prototype.getUpdatedPower = function () {
        return this.updatePowerEE;
    };
    HeroService.prototype.updateBoard = function (board) {
        this.updateBoardEE.emit(board);
    };
    HeroService.prototype.getUpdatedBoard = function () {
        return this.updateBoardEE;
    };
    HeroService.prototype.updateGameSession = function (gameSession) {
        this.updateGameSessionEE.emit(gameSession);
    };
    HeroService.prototype.getUpdatedGameSession = function () {
        return this.updateGameSessionEE;
    };
    HeroService.prototype.handleError = function (error) {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    };
    return HeroService;
}());
__decorate([
    core_1.Output(),
    __metadata("design:type", core_1.EventEmitter)
], HeroService.prototype, "updateHandEE", void 0);
__decorate([
    core_1.Output(),
    __metadata("design:type", core_1.EventEmitter)
], HeroService.prototype, "updatePowerEE", void 0);
__decorate([
    core_1.Output(),
    __metadata("design:type", core_1.EventEmitter)
], HeroService.prototype, "updateBoardEE", void 0);
__decorate([
    core_1.Output(),
    __metadata("design:type", core_1.EventEmitter)
], HeroService.prototype, "updateGameSessionEE", void 0);
HeroService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], HeroService);
exports.HeroService = HeroService;
//# sourceMappingURL=hero.service.js.map