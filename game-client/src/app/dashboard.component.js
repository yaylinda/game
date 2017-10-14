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
var DashboardComponent = (function () {
    function DashboardComponent(heroService) {
        this.heroService = heroService;
        this.name = '';
        this.showGameboard = false;
        this.showLoading = false;
        this.numRows = [];
        this.numCols = [];
        this.showWin = false;
        this.showLoss = false;
        this.showTie = false;
        this.gameEnd = false;
    }
    DashboardComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.heroService.getUpdatedGameSession().subscribe(function (gameSession) {
            _this.gameSession = gameSession;
            if (_this.gameSession.state !== 'ONGOING') {
                _this.gameEnd = true;
                if (_this.gameSession.state === 'WIN') {
                    _this.showWin = true;
                }
                else if (_this.gameSession.state === 'LOSS') {
                    _this.showLoss = true;
                }
                else if (_this.gameSession.state === 'TIE') {
                    _this.showTie = true;
                }
            }
        });
    };
    DashboardComponent.prototype.joinGame = function () {
        var _this = this;
        this.showLoading = true;
        this.heroService.joinGame(this.name)
            .then(function (player) {
            _this.player = player;
            if (_this.player.team === 'TEAM1') {
                _this.heroService.pollForGame(player.id)
                    .subscribe(function (gameSession) {
                    _this.setupGame(gameSession);
                });
            }
            else if (_this.player.team === 'TEAM2') {
                _this.heroService.getPlayerById(player.opponentId)
                    .then(function (player1) {
                    _this.heroService.startGame(player1, player, player.id)
                        .then(function (gameSession) {
                        _this.setupGame(gameSession);
                        _this.heroService.pollForGame(player.id).subscribe(function (gameSession) {
                            _this.setupGame(gameSession);
                        });
                    });
                });
            }
        });
    };
    DashboardComponent.prototype.setupGame = function (gameSession) {
        this.gameSession = gameSession;
        this.showGameboard = true;
        this.showLoading = false;
        this.numRows = Array.from(Array(gameSession.numRows), function (x, i) { return i; });
        this.numCols = Array.from(Array(gameSession.numCols), function (x, i) { return i; });
    };
    return DashboardComponent;
}());
DashboardComponent = __decorate([
    core_1.Component({
        selector: 'my-dashboard',
        template: "\n    <div class=\"main-container\">\n\n      <div id=\"greeting\" *ngIf=\"!showLoading && !showGameboard\">\n        <input id=\"player-name-input\" [(ngModel)]=\"name\" placeholder=\"name\" />\n        <button [disabled]=\"name.length === 0\" id=\"join-game-btn\" (click)=\"joinGame()\"><b>Join Game</b></button>\n      </div>\n\n      <div id=\"loading\" *ngIf=\"showLoading\">\n        Loading <strong>{{name}}'s</strong> Simple War...\n      </div>\n\n      <div id=\"game\" *ngIf=\"showGameboard\">\n        <game-board [gameSession]=\"gameSession\" [numRows]=\"numRows\" [numCols]=\"numCols\"></game-board>\n        <hand [gameSession]=\"gameSession\"></hand>\n        <game-end *ngIf=\"gameEnd\" [status]=\"gameSession.state\" [player]=\"gameSession.player\"></game-end>\n      </div>\n\n    </div>\n  ",
        styleUrls: ['./dashboard.component.css']
    }),
    __metadata("design:paramtypes", [game_service_1.GameService])
], DashboardComponent);
exports.DashboardComponent = DashboardComponent;
//# sourceMappingURL=dashboard.component.js.map