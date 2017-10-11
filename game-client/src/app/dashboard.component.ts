import {Component, OnInit} from '@angular/core';

import {HeroService} from './hero.service';
import {Player} from "./player";
import {GameSession} from "./gamesession";
import {Cell} from "./cell";

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
      let board = Object.assign([], gameSession.gameboard);

      console.log('board BEFORE move:');
      console.log(board);

      for (let rowNum = 0; rowNum < gameSession.numRows; rowNum++) {
        for (let colNum = 0; colNum < gameSession.numCols; colNum++) {
          let cell = board[rowNum][colNum];
          if (cell.type === 'TROOP' && cell.state === 'OCCUPIED' && cell.team === gameSession.player.team) {
            console.log('OLD row num: ' + rowNum);
            let newRowNum = rowNum - cell.move;
            console.log('NEW row num: ' + newRowNum);
            if (newRowNum >= 0) {
              let newCell: Cell = Object.assign({}, cell);
              board[newRowNum][colNum] = newCell;
            } else {
              // TODO update score
              // TODO check for win
              this.gameSession.points += 1;
            }
            board[rowNum][colNum].state = 'EMPTY';
          }
        }
      }

      console.log('board AFTER move:');
      console.log(board);

      this.gameSession.gameboard = board;
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
