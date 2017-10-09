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
var card_1 = require("./card");
var CardComponent = (function () {
    function CardComponent() {
    }
    Object.defineProperty(CardComponent.prototype, "card", {
        get: function () {
            return this._card;
        },
        set: function (card) {
            this._card = card;
        },
        enumerable: true,
        configurable: true
    });
    return CardComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", card_1.Card),
    __metadata("design:paramtypes", [card_1.Card])
], CardComponent.prototype, "card", null);
CardComponent = __decorate([
    core_1.Component({
        selector: 'card',
        template: "\n    <div id=\"card\" [ngClass]=\"card.clicked ? 'highlight-card' : ''\">\n      <p>{{card.cardType}}</p>\n      <p>Cost: {{card.cost}}</p>\n      <p>Might: {{card.might}}</p>\n      <p>Movement: {{card.movement}}</p>\n    </div>\n  ",
        styleUrls: ['./card.component.css']
    })
], CardComponent);
exports.CardComponent = CardComponent;
//# sourceMappingURL=card.component.js.map