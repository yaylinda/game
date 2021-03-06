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
var cell_1 = require("./dto/cell");
var move_1 = require("./dto/move");
var GameService = (function () {
    function GameService(http) {
        this.http = http;
        this.headers = new http_1.Headers({
            'Content-Type': 'application/json',
            'accept': 'application/json'
        });
        this.serverHost = "http://" + window.location.hostname;
        this.serverPort = '8080';
        this.baseUrl = this.serverHost + ":" + this.serverPort;
        this.registerUrl = '/user/';
        this.loginUrl = '/user/login';
        this.playerUrl = '/player';
        this.joinGameUrl = '/player/join';
        this.startGameUrl = '/game/start';
        this.cardUrl = '/game/card';
        this.turnUrl = '/game/endTurn';
        this.pollUrl = '/game/poll';
        this.updateHandEE = new core_1.EventEmitter();
        this.updatePowerEE = new core_1.EventEmitter();
        this.updateBoardEE = new core_1.EventEmitter();
        this.updateGameSessionEE = new core_1.EventEmitter();
    }
    GameService.prototype.login = function (loginRequest) {
        var url = this.baseUrl + this.loginUrl;
        console.log(loginRequest);
        return this.http
            .post(url, loginRequest)
            .toPromise()
            .then(function (response) { return response.json(); });
    };
    GameService.prototype.register = function (registerRequest) {
        var url = this.baseUrl + this.registerUrl;
        return this.http
            .post(url, registerRequest)
            .toPromise()
            .then(function (response) { return response.json(); });
    };
    /*
    OLD FUNCTIONS
     */
    GameService.prototype.joinGame = function (name) {
        this.serverHost = "http://" + window.location.hostname + ":" + this.serverPort;
        console.log('BASE URL: ' + this.serverHost);
        var url = "" + this.serverHost + this.joinGameUrl + "/" + name;
        return this.http
            .post(url, "{}", { headers: this.headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    GameService.prototype.startGame = function (player1, player2, id) {
        console.log("starting game: " + id);
        var url = "" + this.serverHost + this.startGameUrl + "/" + id;
        return this.http
            .post(url, [player1, player2], { headers: this.headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    GameService.prototype.getPlayerById = function (id) {
        console.log("getting player by id: " + id);
        var url = "" + this.serverHost + this.playerUrl + "/" + id;
        return this.http
            .get(url, { headers: this.headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    GameService.prototype.pollForGame = function (id) {
        var _this = this;
        console.log('polling for game...');
        var url = "" + this.serverHost + this.pollUrl + "/" + id;
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
    GameService.prototype.drawCard = function (id) {
        console.log('drawing card...');
        var url = "" + this.serverHost + this.cardUrl + "/" + id;
        return this.http
            .get(url, { headers: this.headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    GameService.prototype.sendCardPut = function (card, row, col) {
        console.log('send card placement...');
        var cell = new cell_1.Cell;
        cell.type = card.cardType;
        cell.might = card.might;
        cell.move = card.movement;
        cell.team = card.owningTeam;
        cell.state = 'OCCUPIED';
        var move = new move_1.Move;
        move.row = row;
        move.col = col;
        move.cell = cell;
        move.playerId = card.owningPlayer;
        var url = "" + this.serverHost + this.cardUrl;
        return this.http
            .put(url, move, { headers: this.headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    GameService.prototype.endTurn = function (id, hand) {
        console.log('ending turn...');
        var url = "" + this.serverHost + this.turnUrl + "/" + id;
        return this.http
            .put(url, hand, { headers: this.headers })
            .toPromise()
            .then()
            .catch(this.handleError);
    };
    /************************************************************************************************
     * Private Helper Functions
     ************************************************************************************************/
    GameService.prototype.handleError = function (error) {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    };
    /************************************************************************************************
     * Getters and Setters
     ************************************************************************************************/
    GameService.prototype.setClickedCard = function (card) {
        this.selectedCard = card;
    };
    GameService.prototype.getClickedCard = function () {
        return this.selectedCard;
    };
    GameService.prototype.updateHand = function (newCard) {
        this.updateHandEE.emit(newCard);
    };
    GameService.prototype.getUpdatedHand = function () {
        return this.updateHandEE;
    };
    GameService.prototype.updatePower = function (power) {
        this.updatePowerEE.emit(power);
    };
    GameService.prototype.getUpdatedPower = function () {
        return this.updatePowerEE;
    };
    GameService.prototype.updateBoard = function (board) {
        this.updateBoardEE.emit(board);
    };
    GameService.prototype.getUpdatedBoard = function () {
        return this.updateBoardEE;
    };
    GameService.prototype.updateGameSession = function (gameSession) {
        this.updateGameSessionEE.emit(gameSession);
    };
    GameService.prototype.getUpdatedGameSession = function () {
        return this.updateGameSessionEE;
    };
    return GameService;
}());
__decorate([
    core_1.Output(),
    __metadata("design:type", core_1.EventEmitter)
], GameService.prototype, "updateHandEE", void 0);
__decorate([
    core_1.Output(),
    __metadata("design:type", core_1.EventEmitter)
], GameService.prototype, "updatePowerEE", void 0);
__decorate([
    core_1.Output(),
    __metadata("design:type", core_1.EventEmitter)
], GameService.prototype, "updateBoardEE", void 0);
__decorate([
    core_1.Output(),
    __metadata("design:type", core_1.EventEmitter)
], GameService.prototype, "updateGameSessionEE", void 0);
GameService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], GameService);
exports.GameService = GameService;
//# sourceMappingURL=game.service.js.map