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
var DashboardComponent = (function () {
    function DashboardComponent(heroService) {
        this.heroService = heroService;
        this.heroes = [];
        this.name = '';
        this.showGameboard = false;
    }
    DashboardComponent.prototype.ngOnInit = function () {
        // this.heroService.getHeroes()
        //   .then(heroes => this.heroes = heroes.slice(1, 5));
    };
    DashboardComponent.prototype.joinGame = function () {
        var _this = this;
        this.heroService.joinGame(this.name)
            .then(function (player) {
            _this.player = player;
            console.log(player);
            if (_this.player.team === 'TEAM1') {
            }
            else if (_this.player.team === 'TEAM2') {
                _this.heroService.getPlayerById(player.opponentId)
                    .then(function (player1) {
                    _this.heroService.startGame(player1, player)
                        .then(function (gameSession) {
                        _this.gameSession = gameSession;
                        console.log(_this.gameSession);
                        _this.showGameboard = true;
                    });
                });
            }
        });
    };
    return DashboardComponent;
}());
DashboardComponent = __decorate([
    core_1.Component({
        selector: 'my-dashboard',
        templateUrl: './dashboard.component.html',
        styleUrls: ['./dashboard.component.css']
    }),
    __metadata("design:paramtypes", [hero_service_1.HeroService])
], DashboardComponent);
exports.DashboardComponent = DashboardComponent;
//# sourceMappingURL=dashboard.component.js.map