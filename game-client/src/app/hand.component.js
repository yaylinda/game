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
var HandComponent = (function () {
    function HandComponent(heroService) {
        this.heroService = heroService;
    }
    HandComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.heroService.getUpdatedHand()
            .subscribe(function (newCard) {
            console.log('recieved new card which is......');
            console.log(newCard);
            var index = _this._cards.indexOf(_this.lastSelectedCard);
            _this._cards.splice(index, 1, newCard);
        });
    };
    HandComponent.prototype.processClickedCard = function (card) {
        for (var _i = 0, _a = this._cards; _i < _a.length; _i++) {
            var otherCard = _a[_i];
            otherCard.clicked = false;
        }
        card.clicked = true;
        this.lastSelectedCard = card;
        this.heroService.setClickedCard(card);
    };
    Object.defineProperty(HandComponent.prototype, "cards", {
        get: function () {
            return this._cards;
        },
        set: function (cards) {
            this._cards = cards;
        },
        enumerable: true,
        configurable: true
    });
    return HandComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", Array),
    __metadata("design:paramtypes", [Array])
], HandComponent.prototype, "cards", null);
HandComponent = __decorate([
    core_1.Component({
        selector: 'hand',
        template: "\n    <div id=\"hand\">\n      <card *ngFor=\"let card of cards;\"\n            (click)=\"processClickedCard(card)\"\n            [card]=\"card\">\n      </card>\n    </div>\n  ",
        styleUrls: ['./hand.component.css']
    }),
    __metadata("design:paramtypes", [hero_service_1.HeroService])
], HandComponent);
exports.HandComponent = HandComponent;
//# sourceMappingURL=hand.component.js.map