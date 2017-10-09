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
var GameboardComponent = (function () {
    function GameboardComponent(heroService) {
        this.heroService = heroService;
    }
    GameboardComponent.prototype.processClickedCell = function (row, col) {
        var _this = this;
        console.log("(" + row + ", " + col + ")");
        if (this.myTurn === true) {
            if (row === 4) {
                var card = this.heroService.getClickedCard();
                if (card) {
                    this._gameboard[row][col].type = card.cardType;
                    this._gameboard[row][col].might = card.might;
                    this._gameboard[row][col].move = card.movement;
                    this.heroService.setClickedCard(null);
                    this.heroService.drawCard(card.owningPlayer)
                        .then(function (newCard) {
                        _this.heroService.updateHand(newCard);
                        _this._myTurn = false;
                    });
                }
            }
        }
    };
    Object.defineProperty(GameboardComponent.prototype, "gameboard", {
        get: function () {
            return this._gameboard;
        },
        set: function (gameboard) {
            this._gameboard = gameboard;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GameboardComponent.prototype, "numRows", {
        get: function () {
            return this._rowIndexes;
        },
        set: function (numRows) {
            this._rowIndexes = numRows;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GameboardComponent.prototype, "numCols", {
        get: function () {
            return this._colndexes;
        },
        set: function (numCols) {
            this._colndexes = numCols;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GameboardComponent.prototype, "myTurn", {
        get: function () {
            return this._myTurn;
        },
        set: function (myTurn) {
            this._myTurn = myTurn;
        },
        enumerable: true,
        configurable: true
    });
    return GameboardComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", Object),
    __metadata("design:paramtypes", [Object])
], GameboardComponent.prototype, "gameboard", null);
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
__decorate([
    core_1.Input(),
    __metadata("design:type", Boolean),
    __metadata("design:paramtypes", [Boolean])
], GameboardComponent.prototype, "myTurn", null);
GameboardComponent = __decorate([
    core_1.Component({
        selector: 'game-board',
        template: "\n    <table id=\"gameboard\">\n      <tr *ngFor=\"let rowNum of numRows\">\n        <td *ngFor=\"let colNum of numCols\" (click)=\"processClickedCell(rowNum, colNum)\" [ngClass]=\"(myTurn && rowNum === 4) ? 'myTurn' : ''\">\n          <p>{{gameboard[rowNum][colNum].type}}</p>\n          <p>{{gameboard[rowNum][colNum].might}}</p>\n          <p>{{gameboard[rowNum][colNum].move}}</p>\n        </td>\n      </tr>\n    </table>\n  ",
        styleUrls: ['./gameboard.component.css']
    }),
    __metadata("design:paramtypes", [hero_service_1.HeroService])
], GameboardComponent);
exports.GameboardComponent = GameboardComponent;
//# sourceMappingURL=gameboard.component.js.map