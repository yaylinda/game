import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';
import {DashboardComponent} from './dashboard.component';
import {GameService} from './game.service';
import {HttpClientModule} from '@angular/common/http';
import {CardComponent} from "./card.component";
import {GameboardComponent} from "./gameboard.component";
import {HandComponent} from "./hand.component";
import {GameEndComponent} from "./gameend.component";
import {LandingComponent} from './landing.component';
import {CookieService} from 'angular2-cookie/core';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    LandingComponent,
    DashboardComponent,
    CardComponent,
    HandComponent,
    GameboardComponent,
    GameEndComponent
  ],
  providers: [
    GameService,
    CookieService
  ],
  bootstrap: [
    AppComponent
  ]
})

export class AppModule { }
