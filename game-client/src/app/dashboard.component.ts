import { Component, OnInit } from '@angular/core';

import { Hero }        from './hero';
import { HeroService } from './hero.service';
import {Player} from "./player";

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
  gameSession: {};

  constructor(private heroService: HeroService) { }

  ngOnInit(): void {
    // this.heroService.getHeroes()
    //   .then(heroes => this.heroes = heroes.slice(1, 5));
  }

  joinGame(): void {
    this.heroService.joinGame(this.name)
      .then(player => {
        this.player = player;
        console.log(player);
        if (this.player.team === 'TEAM1') {

        } else if (this.player.team === 'TEAM2') {
          this.heroService.getPlayerById(player.opponentId)
            .then(player1 => {
              this.heroService.startGame(player1, player)
                .then(gameSession => {
                  this.gameSession = gameSession;
                  console.log(this.gameSession);
                  this.showGameboard = true;
              });
            });
        }
      });
  }

}
