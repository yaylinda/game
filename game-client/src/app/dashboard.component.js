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
        this.name = '';
        this.showGameboard = false;
        this.showLoading = false;
        this.numRows = [];
        this.numCols = [];
    }
    DashboardComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.heroService.getUpdatedGameSession().subscribe(function (gameSession) {
            _this.gameSession = gameSession;
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
        console.log(this.gameSession.gameboard);
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