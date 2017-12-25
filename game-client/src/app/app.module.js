"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var forms_1 = require("@angular/forms");
var http_1 = require("@angular/http");
var app_routing_module_1 = require("./app-routing.module");
var app_component_1 = require("./app.component");
var dashboard_component_1 = require("./dashboard.component");
var game_service_1 = require("./game.service");
var http_2 = require("@angular/common/http");
var card_component_1 = require("./card.component");
var gameboard_component_1 = require("./gameboard.component");
var hand_component_1 = require("./hand.component");
var gameend_component_1 = require("./gameend.component");
var landing_component_1 = require("./landing.component");
var core_2 = require("angular2-cookie/core");
var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    core_1.NgModule({
        imports: [
            platform_browser_1.BrowserModule,
            forms_1.FormsModule,
            http_1.HttpModule,
            app_routing_module_1.AppRoutingModule,
            http_2.HttpClientModule
        ],
        declarations: [
            app_component_1.AppComponent,
            landing_component_1.LandingComponent,
            dashboard_component_1.DashboardComponent,
            card_component_1.CardComponent,
            hand_component_1.HandComponent,
            gameboard_component_1.GameboardComponent,
            gameend_component_1.GameEndComponent
        ],
        providers: [
            game_service_1.GameService,
            core_2.CookieService
        ],
        bootstrap: [
            app_component_1.AppComponent
        ]
    })
], AppModule);
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map