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
var player_1 = require("./dto/player");
var GameEndComponent = (function () {
    function GameEndComponent() {
    }
    Object.defineProperty(GameEndComponent.prototype, "status", {
        get: function () {
            return this._status;
        },
        set: function (status) {
            this._status = status;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GameEndComponent.prototype, "player", {
        get: function () {
            return this._player;
        },
        set: function (value) {
            this._player = value;
        },
        enumerable: true,
        configurable: true
    });
    return GameEndComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", String),
    __metadata("design:paramtypes", [String])
], GameEndComponent.prototype, "status", null);
__decorate([
    core_1.Input(),
    __metadata("design:type", player_1.Player),
    __metadata("design:paramtypes", [player_1.Player])
], GameEndComponent.prototype, "player", null);
GameEndComponent = __decorate([
    core_1.Component({
        selector: 'game-end',
        template: "\n    <h1 *ngIf=\"status === 'WIN'\">You Win!</h1>\n    <h1 *ngIf=\"status === 'LOSS'\">You Lost!</h1>\n    <h1 *ngIf=\"status === 'TIE'\">Tie Game!</h1>\n    <h3>{{player.name}} vs. {{player.opponentName}}</h3>\n    <h4>{{player.score}} - {{player.opponentScore}}</h4>\n  ",
        styleUrls: ['./gameend.component.css']
    })
], GameEndComponent);
exports.GameEndComponent = GameEndComponent;
//# sourceMappingURL=gameend.component.js.map