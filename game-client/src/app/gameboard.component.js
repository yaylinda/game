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
var hero_service_1 = require("./hero.service");
var gamesession_1 = require("./gamesession");
var GameboardComponent = (function () {
    function GameboardComponent(heroService) {
        this.heroService = heroService;
    }
    GameboardComponent.prototype.processClickedCell = function (row, col) {
        var _this = this;
        console.log("clicked on: (" + row + ", " + col + ")");
        if (this._gameSession.myTurn === true) {
            if (row === 4) {
                if (this._gameSession.gameboard[row][col].state === 'EMPTY') {
                    var card = this.heroService.getClickedCard();
                    if (card) {
                        if (this._gameSession.player.power >= card.cost) {
                            this._gameSession.gameboard[row][col].state = 'OCCUPIED';
                            this._gameSession.gameboard[row][col].type = card.cardType;
                            this._gameSession.gameboard[row][col].might = card.might;
                            this._gameSession.gameboard[row][col].move = card.movement;
                            this._gameSession.gameboard[row][col].team = card.owningTeam;
                            this._gameSession.player.power = this._gameSession.player.power - card.cost;
                            this.heroService.updatePowerEE.emit(this._gameSession.player.power);
                            this.heroService.updateBoardEE.emit(this._gameSession.gameboard);
                            this.heroService.setClickedCard(null);
                            this.heroService.drawCard(card.owningPlayer)
                                .then(function (newCard) {
                                _this.heroService.updateHand(newCard);
                            });
                        }
                    }
                }
            }
        }
    };
    Object.defineProperty(GameboardComponent.prototype, "gameSession", {
        get: function () {
            return this._gameSession;
        },
        set: function (gameSession) {
            this._gameSession = gameSession;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GameboardComponent.prototype, "numRows", {
        get: function () {
            return this._numRows;
        },
        set: function (value) {
            this._numRows = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GameboardComponent.prototype, "numCols", {
        get: function () {
            return this._numCols;
        },
        set: function (value) {
            this._numCols = value;
        },
        enumerable: true,
        configurable: true
    });
    return GameboardComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", gamesession_1.GameSession),
    __metadata("design:paramtypes", [gamesession_1.GameSession])
], GameboardComponent.prototype, "gameSession", null);
__decorate([
    core_1.Input(),
    __metadata("design:type", Array),
    __metadata("design:paramtypes", [Array])
], GameboardComponent.prototype, "numRows", null);
__decorate([
    core_1.Input(),
    __metadata("design:type", Array),
    __metadata("design:paramtypes", [Array])
], GameboardComponent.prototype, "numCols", null);
GameboardComponent = __decorate([
    core_1.Component({
        selector: 'game-board',
        template: "\n    <table id=\"gameboard\">\n      <tr *ngFor=\"let rowNum of numRows\">\n        <td *ngFor=\"let colNum of numCols\" \n            (click)=\"processClickedCell(rowNum, colNum)\" \n            [ngClass]=\"[(gameSession.myTurn && rowNum === 4) ? 'playable' : '', (gameSession.gameboard[rowNum][colNum].team === 'TEAM1') ? 'team1' : 'team2']\">\n          <p *ngIf=\"gameSession.gameboard[rowNum][colNum].state === 'OCCUPIED'\">{{gameSession.gameboard[rowNum][colNum].type}}</p>\n          <p *ngIf=\"gameSession.gameboard[rowNum][colNum].state === 'OCCUPIED'\">{{gameSession.gameboard[rowNum][colNum].might}}</p>\n          <p *ngIf=\"gameSession.gameboard[rowNum][colNum].state === 'OCCUPIED'\">{{gameSession.gameboard[rowNum][colNum].move}}</p>\n        </td>\n      </tr>\n    </table>\n  ",
        styleUrls: ['./gameboard.component.css']
    }),
    __metadata("design:paramtypes", [hero_service_1.HeroService])
], GameboardComponent);
exports.GameboardComponent = GameboardComponent;
//# sourceMappingURL=gameboard.component.js.map