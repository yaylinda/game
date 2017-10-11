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
var HandComponent = (function () {
    function HandComponent(heroService) {
        this.heroService = heroService;
    }
    HandComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.heroService.getUpdatedHand()
            .subscribe(function (newCard) {
            var index = _this._gameSession.player.hand.indexOf(_this._lastSelectedCard);
            _this._gameSession.player.hand.splice(index, 1, newCard);
        });
        this.heroService.getUpdatedPower()
            .subscribe(function (power) {
            _this._gameSession.player.power = power;
        });
        this.heroService.getUpdatedBoard()
            .subscribe(function (board) {
            _this._gameSession.gameboard = board;
        });
    };
    HandComponent.prototype.processClickedCard = function (card) {
        if (this._gameSession.myTurn) {
            for (var _i = 0, _a = this._gameSession.player.hand; _i < _a.length; _i++) {
                var otherCard = _a[_i];
                otherCard.clicked = false;
            }
            card.clicked = true;
            this._lastSelectedCard = card;
            this.heroService.setClickedCard(card);
        }
    };
    HandComponent.prototype.endTurn = function () {
        var _this = this;
        if (this._gameSession.myTurn) {
            this._gameSession.myTurn = false;
            this.heroService.endTurn(this._gameSession);
            this.heroService.pollForGame(this._gameSession.player.id).subscribe(function (updatedGameSession) {
                _this.heroService.updateGameSessionEE.emit(updatedGameSession);
            });
        }
        else {
            console.log('it\'s not even your turn');
        }
    };
    Object.defineProperty(HandComponent.prototype, "gameSession", {
        get: function () {
            return this._gameSession;
        },
        set: function (gameSession) {
            this._gameSession = gameSession;
        },
        enumerable: true,
        configurable: true
    });
    return HandComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", gamesession_1.GameSession),
    __metadata("design:paramtypes", [gamesession_1.GameSession])
], HandComponent.prototype, "gameSession", null);
HandComponent = __decorate([
    core_1.Component({
        selector: 'hand',
        template: "\n    <div id=\"hand\">\n      <div id=\"game-stats\">\n        <p *ngIf=\"gameSession.myTurn\">My Turn</p>\n        <p *ngIf=\"!gameSession.myTurn\">Opponent's Turn</p>\n        <p>Power: {{gameSession.player.power}}</p>\n      </div>\n      <card *ngFor=\"let card of gameSession.player.hand;\"\n            (click)=\"processClickedCard(card)\"\n            [card]=\"card\">\n      </card>\n      <button id=\"end-turn-btn\" (click)=\"endTurn()\">End Turn</button>\n    </div>\n  ",
        styleUrls: ['./hand.component.css']
    }),
    __metadata("design:paramtypes", [hero_service_1.HeroService])
], HandComponent);
exports.HandComponent = HandComponent;
//# sourceMappingURL=hand.component.js.map