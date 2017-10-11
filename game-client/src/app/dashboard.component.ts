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
      // TODO check for loss state
      // TODO handle loss
      let board = Object.assign([], gameSession.gameboard);
      for (let rowNum = 0; rowNum < gameSession.numRows; rowNum++) {
        for (let colNum = 0; colNum < gameSession.numCols; colNum++) {
          let cell: Cell = Object.assign({}, board[rowNum][colNum]);
          if (cell.type === 'TROOP' && cell.state === 'OCCUPIED' && cell.team === gameSession.player.team) {
            let newRowNum = rowNum - cell.move;
            if (newRowNum >= 0) {
              let newCell: Cell = Object.assign({}, board[newRowNum][colNum]);
              if (newCell.state === 'OCCUPIED') {
                if (newCell.team == gameSession.player.team) {
                  cell.might = cell.might + newCell.might;
                } else {
                  let mightDiff = cell.might - newCell.might;
                  if (mightDiff === 0) {
                    cell.state = 'EMPTY';
                  } else if (mightDiff > 0) {
                    cell.might = mightDiff;
                  } else if (mightDiff < 0) {
                    cell.might = mightDiff * -1;
                    cell.move = newCell.move;
                    cell.team = newCell.team;
                    cell.type = newCell.type;
                  }
                }
              }
              board[newRowNum][colNum] = Object.assign({}, cell);
            } else {
              console.log('scored a point!');
              this.gameSession.player.score += 1;
              if (this.gameSession.player.score >= this.gameSession.player.maxScore) {
                console.log('YOU WIN!!!!');
                // TODO send win to backend
                // TODO handle win
              }
            }
            board[rowNum][colNum].state = 'EMPTY';
          }
        }
      }
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
  }
}
