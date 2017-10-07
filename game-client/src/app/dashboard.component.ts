import { Component, OnInit } from '@angular/core';

import { Hero }        from './hero';
import { HeroService } from './hero.service';
import {Player} from "./player";
import {GameSession} from "./gamesession";

@Component({
  selector: 'my-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})

export class DashboardComponent implements OnInit {
  heroes: Hero[] = [];
  name = '';
  player: Player;
  showGameboard = false;
  showLoading = false;
  gameSession: GameSession;

  constructor(private heroService: HeroService) { }

  ngOnInit(): void {
    // this.heroService.getHeroes()
    //   .then(heroes => this.heroes = heroes.slice(1, 5));
  }

  joinGame(): void {
    this.showLoading = true;
    this.heroService.joinGame(this.name)
      .then(player => {
        this.player = player;
        if (this.player.team === 'TEAM1') {
          this.heroService.pollForGame(player.id)
            .subscribe(gameSession => {
              console.log('got result from polling...');
              this.gameSession = gameSession;
              console.log(this.gameSession);
              this.showGameboard = true;
              this.showLoading = false;
          });
        } else if (this.player.team === 'TEAM2') {
          this.heroService.getPlayerById(player.opponentId)
            .then(player1 => {
              console.log("got player!");
              this.heroService.startGame(player1, player)
                .then(gameSession => {
                  this.gameSession = gameSession;
                  console.log(this.gameSession);
                  this.showGameboard = true;
                  this.showLoading = false;
                });
            });
        }
      });
  }

}
