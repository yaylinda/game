import { Component, OnInit } from '@angular/core';

import { Hero }        from './hero';
import { HeroService } from './hero.service';
import {Player} from "./player";
import {GameSession} from "./gamesession";
import {CardFrontComponent} from "./card-front.component";

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
  numRows: number[] = [];
  numCols: number[] = [];

  constructor(private heroService: HeroService) { }

  ngOnInit(): void { }

  joinGame(): void {
    this.showLoading = true;
    this.heroService.joinGame(this.name)
      .then(player => {
        this.player = player;
        if (this.player.team === 'TEAM1') {
          this.heroService.pollForGame(player.id)
            .subscribe(gameSession => {
              this.setupGame(gameSession);
          });
        } else if (this.player.team === 'TEAM2') {
          this.heroService.getPlayerById(player.opponentId)
            .then(player1 => {
              this.heroService.startGame(player1, player)
                .then(gameSession => {
                  this.setupGame(gameSession);
                });
            });
        }
      });
  }

  setupGame(gameSession: GameSession): void {
    this.gameSession = gameSession;
    this.showGameboard = true;
    this.showLoading = false;
    this.numRows = Array.from(Array(gameSession.gameBoard.numRows),(x,i)=>i);
    this.numCols = Array.from(Array(gameSession.gameBoard.numCols),(x,i)=>i);
    console.log(this.gameSession.gameBoard);
  }

}
