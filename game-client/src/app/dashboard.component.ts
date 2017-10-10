import {Component, OnInit} from '@angular/core';

import {HeroService} from './hero.service';
import {Player} from "./player";
import {GameSession} from "./gamesession";

@Component({
  selector: 'my-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})

export class DashboardComponent implements OnInit {
  name = '';
  player: Player;
  showGameboard = false;
  showLoading = false;
  gameSession: GameSession;
  numRows: number[] = [];
  numCols: number[] = [];

  constructor(private heroService: HeroService) { }

  ngOnInit(): void {
    this.heroService.getUpdatedGameSession().subscribe((gameSession: GameSession) => {
      this.gameSession = gameSession;
    });
  }

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
              this.heroService.startGame(player1, player, player.id)
                .then(gameSession => {
                  this.setupGame(gameSession);
                  this.heroService.pollForGame(player.id).subscribe(gameSession => {
                    this.setupGame(gameSession);
                  });
                });
            });
        }
      });
  }

  setupGame(gameSession: GameSession): void {
    this.gameSession = gameSession;
    this.showGameboard = true;
    this.showLoading = false;
    this.numRows = Array.from(Array(gameSession.numRows),(x,i)=>i);
    this.numCols = Array.from(Array(gameSession.numCols),(x,i)=>i);
    console.log(this.gameSession.gameboard);
  }
}
